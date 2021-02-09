package org.arch.ums.feign.account.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.val;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.HashMap;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

/**
 * Adds support for {@link MultipartFile} type to {@link FormEncoder}.
 *
 * @author Tomasz Juchniewicz &lt;tjuchniewicz@gmail.com&gt;
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.4 16:37
 */
public class ArchSpringFormEncoder extends FormEncoder {

    /**
     * Constructor with the default Feign's encoder as a delegate.
     */
    public ArchSpringFormEncoder () {
        this(new Encoder.Default());
    }

    /**
     * Constructor with specified delegate encoder.
     *
     * @param delegate  delegate encoder, if this encoder couldn't encode object.
     */
    public ArchSpringFormEncoder (Encoder delegate) {
        super(delegate);

        val processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
        processor.addFirstWriter(new SpringSingleMultipartFileWriter());
        processor.addFirstWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode (Object object, Type bodyType, RequestTemplate template) throws EncodeException {



        if (bodyType.equals(MultipartFile[].class)) {
            val files = (MultipartFile[]) object;
            val data = new HashMap<String, Object>(files.length, 1.F);
            for (val file : files) {
                data.put(file.getName(), file);
            }

            // TODO 增加对上传参数注解参数名称的解析

            // data 添加数组
            if (files.length == 1) {

                data.put(files[0].getName(), files[0]);
            }
            else //noinspection AlibabaUndefineMagicConstant
                if (files.length >= 2) {
                data.put(files[0].getName(), files);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else if (bodyType.equals(MultipartFile.class)) {
            val file = (MultipartFile) object;
            val data = singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else if (isMultipartFileCollection(object)) {
            val iterable = (Iterable<?>) object;
            val data = new HashMap<String, Object>();
            for (val item : iterable) {
                val file = (MultipartFile) item;
                data.put(file.getName(), file);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else {
            super.encode(object, bodyType, template);
        }
    }

    private boolean isMultipartFileCollection (Object object) {
        if (!(object instanceof Iterable)) {
            return false;
        }
        val iterable = (Iterable<?>) object;
        val iterator = iterable.iterator();
        return iterator.hasNext() && iterator.next() instanceof MultipartFile;
    }
}
