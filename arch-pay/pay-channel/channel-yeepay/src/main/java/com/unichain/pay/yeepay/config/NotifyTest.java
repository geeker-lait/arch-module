package com.unichain.pay.yeepay.config;

import com.yeepay.g3.sdk.yop.encrypt.DigitalEnvelopeDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class NotifyTest {

    // 项目的私钥，测试数据用测试的秘钥，等以后正式商编发起请求要更换成正式的，自己生成的。
    static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrS0KstpuJs75jrO2Tc7x5WfeQ8ER1tPr34OPiJfETinvTQeNzZc13V1Wm+yhk5Xsv4OWuSHCok5MwhEgc4cb03loTcS4pqAHL4u13hEycf+fTkoIQ88IpmFMcnu0l3dYqzj34fEgH7EN4byCDDd9cNtZWuzA9iitwJuoCjx4sJqxvftxPQV6gbPZEwIWPw3i1ALG8LgKDRiB/i8hw4PfRCGrYtwjOjwKsiEetxNUpROs8nUSpRBQnr8G1Z85sN6wH83eH3y2uJ4O4G/Eao3FG8rHVWPIiQaXtyFqFx8B3pFAwqYuxWaciInlNPTLMd1Ee+BDqQ9FN/tzy/q5CSTevAgMBAAECggEBAJiAYbT4wpMfNrLxI3ayhKsZgQJGFv0gioujad9OXkpCcamMsJ5tlTbZx0TpuHXTpQ/kTzgSAFLlSBbavoUQMZySVWmXyzyE+kx2FWrhm399lHzVo/zJuCRmHCCQEZwz21ey1JNkupBrNUqEzVJASIqFu9/tua4gVDn+OzraBkfREWV9E6CmQ0xl7SNz9awWpg/8zTqLMsXSR3Q6+ezd5vHZTZvkG5sLh2cKgylZoZawJgrGpW5mt+nXlPhvXmW6XcX730DWNvTgIOtduDZ0Ig5JEya8V9XC+1OcHAkpXXMG2ohABZtIKKS4yLWSwgCFErgVyien5T2kdEIGPbnE55ECgYEA88r2DHhssH9fSIzHo6yL3VMit30Qs6fjxmQXcqxewYtk11yPpn2ROaGBYitzsv/kgVLECRsfIREGsUjPqz7Al8NpgqoQEf27zWaf6x59cs3g2vB6rtilvng8C3dnSFjKZQ/tbXv/QHDz/A549rwm4fO689ptjegS3zcAaptF7okCgYEAs976k3NCpln7FzGKtMrMk5my/ed8Dj70cntxdwNBcsOhbbh1HbCsn2F/PLdqd6bJc+Qdi7hIGecUZ/6/CuEDY/8jZqm9b6B4vnxZFBTUyqHcL8cXmMUDi9p7SoSsCZ92RCD0x4SbOjhym7yuvw14gk+hlBSvWuC+YhEMRlinJncCgYBJ+12Pizvwk7amnZI36TTIhWITrLBU1K4almVHN2fJ9DM157DwJUrc4lYRJH6H43/EfwleegyITFJrmlzq6rAnXfW24UTfMNC9FFeTUj1fiXqi9jdEuBoUIwiVsjZ1jfxdjufOQcLEG4LvCrVKqu5hw0UIm1CDr9mKQ3as41HlgQKBgCjBD+NSzTolzxdtOTFHddzHiV+wEFKl/vrlb0r46N5Y5v2WOqr0edhO3eZi5HOhzak9eVhL88IyslPxy1VqsDr69wlu0iY1pMX8JK7BHYmf7OTCZl1N3kTUxvSWZOh1QfWjxfJi4Ezrt0QEF0/gfHqCEmkb2rNrkpdjp3VU5uJ3AoGBANhQ1Jl3X3UOVXQm0ZXQ7OkPkWE5XZIOb+WUcB7JTXKmpAArqI2FOe3Ki+8rMWDNgDUu+/nvSSWzrAsG8+jR9Hp+fSQ6poagwpWcitPjFYQip6RUBx6Cv6mBCMXDGnxOQIi8GJCITT86SsF1YbirDmi/Yd58xPEBbQdjysRWhr8e";

    // 项目的公钥，这个公钥都是用同一个，之后正式也不用换
    static String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6p0XWjscY+gsyqKRhw9MeLsEmhFdBRhT2emOck/F1Omw38ZWhJxh9kDfs5HzFJMrVozgU+SJFDONxs8UB0wMILKRmqfLcfClG9MyCNuJkkfm0HFQv1hRGdOvZPXj3Bckuwa7FrEXBRYUhK7vJ40afumspthmse6bs6mZxNn/mALZ2X07uznOrrc2rk41Y2HftduxZw6T4EmtWuN2x4CZ8gwSyPAW5ZzZJLQ6tZDojBK4GZTAGhnn3bg5bBsBlw2+FLkCQBuDsJVsFPiGh/b6K/+zGTvWyUcu+LUj2MejYQELDO3i2vQXVDk7lVi2/TcUYefvIcssnzsfCfjaorxsuwIDAQAB";

    public static void main(String[] args) {

		/*回调的时候接request里面的2个参数  POST 方式 Content-Type : application/x-www-form-urlencoded
		String responseMsg = request.getParameter("response");
		String customerId = request.getParameter("customerIdentification") */
        String responseMsg = "U_eLCtj4Z8JjuD52Ny9c8ukC9kM81bBE4dwnajRFITBE5qq97IWGZ0ktpkJ59PrgNcRx0KsvrZo38xqfZsUdiWJwRfJV9COUgMiYCwMy7CB3eDiyJGnYOPEEJ8ojCE9BnI_WCsviX-iS2in0XzHOMyxmArKgrUKugjttApp2fCf-xiiQdARw7FmydfAsngO30TikIHCNVrAV7d3HluKz14jeqG88HgZZ7exoScrwNFI5FVq9OvOOY3245xqw97rBUxZ5wi7nzzAS3he4PV_g7uNTa91AubvwbnECg-5RHsXU_H7u5tyEAgQJdBRzsvfI133Phe2pqZTh4TBzrzMjRw$7x8JpF2aIV4B1GeCv-RDtdjvlpDV2EWWu1h2AG_2lV2nOe_w3OT3tJr9jpTmWq7LnhLJqHLdKV6OwIrBiiOB01RswE4Id9V1Q3R8JE0QLlzGZpfBOHDRdCHu81EHQHEooeNJV_2m6mVQDvvB2o--ebnJaBYZnCUAiFqhT0-FR3GiePnystIHCgsEpxLMR9ThXIoZUG1CC652lLKooXgd5RGM71HVAIQzXugx_T8uU9Kkqd_0IZBkgqV7VXZobmYg6z-Q9_H6EAYVzrX4GjJGHuX81gc75YMMKtzP-pLNsAiaihkarYfi2UXGngr_tOMGaot7epVomrrlsgbDTm1TQHTx1WAiV1xezX6pTyQ_G44IYF625eP0OO0ug-uQTktkAd2gIz6hgredqOldil4dEeVwksnrTA7WT6zB-KJTdHY41eDD_8Xtcj9s-BkGsU1af0ZEzAGk3_3NUEbqlVO4QCxd8a9osu6e9W9ac1dR90VxSx0zVBYm2f0PnIrXSILMICnVpKs8tkT4MmfLuo1rEGqtm9Fr44aIgAS2feBkXYhu1rTFG428ZihsperaAmqV5a7SxybKz1K7xawJ2YcS7V1GsdKZMYuR3iSaPz3icY3-WVHaNHLi8wo8ze0kDKHv8p5edjKfHmZkhzohUnFxvsvIw1O56NWmzkYprwIARmmrwqbUR4GOub4K-8ErbbSwP5djRgHlFQMjqpeUkov5P1nh_2WxMauTiss4CWuPRYMJEobOpgNYpGA4MJwBncNSLtQUY8ucG-fKU4DYfExfPF_M_uYBKH-E5uoVW9HFm0BWIagqMgc2ZtvfIf4XhsZF$AES$SHA256";

        try {
            // 开始解密
            Map<String, String> jsonMap = new HashMap<>();
            DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
            dto.setCipherText(responseMsg);
            PrivateKey privateKey = getPrivateKey();
            System.out.println("privateKey: " + privateKey);
            PublicKey publicKey = getPubKey();
            System.out.println("publicKey: " + publicKey);

            dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
            System.out.println("解密结果:" + dto.getPlainText());
            System.out.println(jsonMap);

            String merchantno = formatString(jsonMap.get("merchantno"));
            String requestno = formatString(jsonMap.get("requestno"));
            String yborderid = formatString(jsonMap.get("yborderid"));
            String status = formatString(jsonMap.get("status"));
            String amount = formatString(jsonMap.get("amount"));
            String cardtop = formatString(jsonMap.get("cardtop"));
            String cardlast = formatString(jsonMap.get("cardlast"));
            String bankcode = formatString(jsonMap.get("bankcode"));
            String errorcode = formatString(jsonMap.get("errorcode"));
            String errormsg = formatString(jsonMap.get("errormsg"));

        } catch (Exception e) {
            throw new RuntimeException("回调解密失败！");
        }
    }

    /**
     * 实例化公钥
     *
     * @return
     */
    private static PublicKey getPubKey() {
        PublicKey publicKey = null;
        try {

            // 自己的公钥(测试)
            String publickey = public_key;// 直接写死 properties json里面的publickey
            java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(publickey));
            // RSA对称加密算法
            KeyFactory keyFactory;
            keyFactory = KeyFactory.getInstance("RSA");
            // 取公钥匙对象
            publicKey = keyFactory.generatePublic(bobPubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 实例化私钥
     *
     * @return
     */
    private static PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        String priKey = private_key;
        PKCS8EncodedKeySpec priPKCS8;
        try {
            priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(priKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }


    public static String formatString(String text) {
        return text == null ? "" : text.trim();
    }
}
