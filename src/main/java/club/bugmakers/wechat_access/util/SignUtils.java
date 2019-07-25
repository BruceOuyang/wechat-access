package club.bugmakers.wechat_access.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * SignUtils 微信验签
 *
 * @Author Bruce
 * @Date 2019/7/24 15:04
 * @Version 1.0
 **/
@Slf4j
public class SignUtils {

    /**
     * 与接口配置信息中的Token要一致
     */
    private static String token = "bruce-token";

    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {

        log.info("check signature: ");
        log.info("-----------------------------------------");

        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);

            log.info("compute signature: {}", tmpStr);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        boolean matched = tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;

        log.info("signature matched: {}", matched);
        log.info("=========================================");

        return matched;
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
