package org.arch.framework.web.feign.encoder;

import feign.Request;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Add springFormEncoder to encode APPLICATION_FORM_URLENCODED
 *
 * @author YongWu zheng
 * @author Spencer Gibb, Scien Jus, Ahmad Mozafarnia, Aaron Whiteside, Darren Foong
 * @weixin z56133
 * @see SpringEncoder
 * @since 2021.3.1 17:44
 */
@SuppressWarnings("rawtypes")
public class ArchSpringEncoder implements Encoder {

    private static final Log log = LogFactory.getLog(ArchSpringEncoder.class);

    private final SpringFormEncoder springFormEncoder;

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public ArchSpringEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.springFormEncoder = new SpringFormEncoder();
        this.messageConverters = messageConverters;
    }

    public ArchSpringEncoder(SpringFormEncoder springFormEncoder, ObjectFactory<HttpMessageConverters> messageConverters) {
        this.springFormEncoder = springFormEncoder;
        this.messageConverters = messageConverters;
    }

    private static HttpHeaders getHttpHeaders(Map<String, Collection<String>> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            httpHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return httpHeaders;
    }

    private static Map<String, Collection<String>> getHeaders(HttpHeaders httpHeaders) {
        LinkedHashMap<String, Collection<String>> headers = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }

        return headers;
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        // template.body(conversionService.convert(object, String.class))
        if (requestBody != null) {
            Collection<String> contentTypes = request.headers().get(HttpEncoding.CONTENT_TYPE);

            MediaType requestContentType = null;
            if (contentTypes != null && !contentTypes.isEmpty()) {
                String type = contentTypes.iterator().next();
                requestContentType = MediaType.valueOf(type);
            }

            // Add springFormEncoder to encode APPLICATION_FORM_URLENCODED
            if (Objects.equals(requestContentType, MediaType.MULTIPART_FORM_DATA)
                    || Objects.equals(requestContentType, MediaType.APPLICATION_FORM_URLENCODED)) {
                this.springFormEncoder.encode(requestBody, bodyType, request);
                return;
            } else {
                if (bodyType == MultipartFile.class) {
                    log.warn("For MultipartFile to be handled correctly, the 'consumes' parameter of @RequestMapping "
                            + "should be specified as MediaType.MULTIPART_FORM_DATA_VALUE");
                }
            }

            for (HttpMessageConverter messageConverter : this.messageConverters.getObject().getConverters()) {
                ArchSpringEncoder.FeignOutputMessage outputMessage;
                try {
                    if (messageConverter instanceof GenericHttpMessageConverter) {
                        outputMessage = checkAndWrite(requestBody, bodyType, requestContentType,
                                (GenericHttpMessageConverter) messageConverter, request);
                    } else {
                        outputMessage = checkAndWrite(requestBody, requestContentType, messageConverter, request);
                    }
                } catch (IOException | HttpMessageConversionException ex) {
                    throw new EncodeException("Error converting request body", ex);
                }
                if (outputMessage != null) {
                    // clear headers
                    request.headers(null);
                    // converters can modify headers, so update the request
                    // with the modified headers
                    request.headers(getHeaders(outputMessage.getHeaders()));

                    // do not use charset for binary data and protobuf
                    Charset charset;
                    if (messageConverter instanceof ByteArrayHttpMessageConverter) {
                        charset = null;
                    } else if (messageConverter instanceof ProtobufHttpMessageConverter
                            && ProtobufHttpMessageConverter.PROTOBUF
                            .isCompatibleWith(outputMessage.getHeaders().getContentType())) {
                        charset = null;
                    } else {
                        charset = StandardCharsets.UTF_8;
                    }
                    request.body(Request.Body.encoded(outputMessage.getOutputStream().toByteArray(), charset));
                    return;
                }
            }
            String message = "Could not write request: no suitable HttpMessageConverter " + "found for request type ["
                    + requestBody.getClass().getName() + "]";
            if (requestContentType != null) {
                message += " and content type [" + requestContentType + "]";
            }
            throw new EncodeException(message);
        }
    }

    @SuppressWarnings("unchecked")
    private FeignOutputMessage checkAndWrite(Object body, MediaType contentType, HttpMessageConverter converter,
                                             RequestTemplate request) throws IOException {
        if (converter.canWrite(body.getClass(), contentType)) {
            logBeforeWrite(body, contentType, converter);
            FeignOutputMessage outputMessage = new FeignOutputMessage(request);
            converter.write(body, contentType, outputMessage);
            return outputMessage;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private FeignOutputMessage checkAndWrite(Object body, Type genericType, MediaType contentType,
                                             GenericHttpMessageConverter converter, RequestTemplate request) throws IOException {
        if (converter.canWrite(genericType, body.getClass(), contentType)) {
            logBeforeWrite(body, contentType, converter);
            FeignOutputMessage outputMessage = new FeignOutputMessage(request);
            converter.write(body, genericType, contentType, outputMessage);
            return outputMessage;
        } else {
            return null;
        }
    }

    private void logBeforeWrite(Object requestBody, MediaType requestContentType,
                                HttpMessageConverter messageConverter) {
        if (log.isDebugEnabled()) {
            if (requestContentType != null) {
                log.debug("Writing [" + requestBody + "] as \"" + requestContentType + "\" using [" + messageConverter
                        + "]");
            } else {
                log.debug("Writing [" + requestBody + "] using [" + messageConverter + "]");
            }
        }
    }

    private final static class FeignOutputMessage implements HttpOutputMessage {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        private final HttpHeaders httpHeaders;

        private FeignOutputMessage(RequestTemplate request) {
            this.httpHeaders = getHttpHeaders(request.headers());
        }

        @NonNull
        @Override
        public OutputStream getBody() throws IOException {
            return this.outputStream;
        }

        @NonNull
        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        public ByteArrayOutputStream getOutputStream() {
            return this.outputStream;
        }

    }

}