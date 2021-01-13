//package org.arch.framework.utils;
//
//import com.baidubce.auth.DefaultBceCredentials;
//import com.baidubce.services.bos.BosClient;
//import com.baidubce.services.bos.BosClientConfiguration;
//import com.baidubce.services.bos.model.BosObject;
//import com.baidubce.services.bos.model.ObjectMetadata;
//import com.baidubce.services.bos.model.PutObjectResponse;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//
///**
// * Description: 百度智能云工具类
// *
// * @date 2020/3/31 10:46
// */
//public class BosClientUtils {
//
//
//    private static BosClient client;
//    @Value("${baidubos.access_key_id}")
//    private String ACCESS_KEY_ID;
//    @Value("${baidubos.secret_access_key}")
//    private String SECRET_ACCESS_KEY;
//    @Value("${baidubos.endpoint}")
//    private String ENDPOINT;
//    @Value("${baidubos.bucketName.public}")
//    private String YUESHANG_SKIT_PUBLIC;
//    @Value("${baidubos.bucketName.private}")
//    private String YUESHANG_SKIT;
//    private static BosClientUtils instance = null;
//
//
//    public static void main(String[] args) throws IOException {
//        System.out.println("==================================");
//        System.out.println("GetSessionToken result:");
//        System.out.println("==================================");
//
//        String endpoint = "http://gz.bcebos.com";
//        String ak = "b0a31c04625d470187a7e0a0719d8689";
//        String sk = "bfe5b63631fd4248bbcbe97ba7203ddf";
//        String bucketNamePublic = "yueshang-skit-public";
//        String bucketNamePrivate = "yueshang-skit";
//
//        BosClientConfiguration config = new BosClientConfiguration();
//        config.setCredentials(new DefaultBceCredentials(ak, sk));
//        config.setEndpoint(endpoint);
//        BosClient client = new BosClient(config);
//
//        //上传图片
//        // 获取数据流
//        //{"eTag":"128aa297ccab81b7f884ca26465b7166"}
////        InputStream inputStream = new FileInputStream("D:\\temp\\test2.jpeg");
////        PutObjectResponse putObjectResponse = client.putObject(bucketNamePrivate, "test/testjpeg", inputStream);
////        inputStream.close();
////        // 打印ETag
////        System.out.println(GsonUtils.toJson(putObjectResponse));
////        System.out.println(GsonUtils.toJson(putObjectResponse));
//
//        URL url = client.generatePresignedUrl(bucketNamePrivate, "test/testjpeg", 180);
//        //指定用户需要获取的Object所在的Bucket名称、该Object名称、时间戳、URL的有效时长
//        System.out.println(url.toString());
//    }
//
//
//
//    /**
//     * 简单上传：byte[]
//     * @param bucketName
//     * @param objectKey
//     * @param bytes
//     */
//    public void putObject(String bucketName, String objectKey, byte[] bytes) throws IOException {
//        client.putObject(bucketName, objectKey, bytes);
//    }
//
//    /**
//     * 简单上传：示例
//     * @param bucketName
//     * @param objectKey
//     * @param byte1
//     * @param string1
//     */
//    private void putObject(String bucketName, String objectKey, byte[] byte1, String string1) throws IOException {
//        // 获取指定文件
//        File file = new File("/path/to/file.zip");
//        // 获取数据流
//        InputStream inputStream = new FileInputStream("/path/to/test.zip");
//
//        // 以文件形式上传Object
//        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file);
//        // 以数据流形式上传Object
//        PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, objectKey, inputStream);
//        inputStream.close();
//        // 以二进制串上传Object
//        PutObjectResponse putObjectResponseFromByte = client.putObject(bucketName, objectKey, byte1);
//        // 以字符串上传Object
//        PutObjectResponse putObjectResponseFromString = client.putObject(bucketName, objectKey, string1);
//
//        // 打印ETag
//        System.out.println(putObjectFromFileResponse.getETag());
//    }
//
//    /**
//     * 简单流式下载,示例
//     * @param client
//     * @param bucketName
//     * @param objectKey
//     * @throws IOException
//     */
//    private void getObject(String bucketName, String objectKey)
//            throws IOException {
//
//        // 获取Object，返回结果为BosObject对象
//        BosObject object = client.getObject(bucketName, objectKey);
//
//        // 获取ObjectMeta
//        ObjectMetadata meta = object.getObjectMetadata();
//        // 获取Object的输入流
//        InputStream objectContent = object.getObjectContent();
//        // 处理Object
//        // 关闭流
//        objectContent.close();
//    }
//
//    /**
//     * 获取文件下载URL
//     * @param client
//     * @param bucketName Bucket名称
//     * @param objectKey Object名称
//     * @param expirationInSeconds URL的有效时长
//     * @return
//     */
//    public String generatePresignedUrl(String bucketName, String objectKey, int expirationInSeconds) {
//
//        URL url = client.generatePresignedUrl(bucketName, objectKey, expirationInSeconds);
//        //指定用户需要获取的Object所在的Bucket名称、该Object名称、时间戳、URL的有效时长
//
//        return url.toString();
//    }
//}