package cn.ml.saddhu.bihudaily.engine.util;

import java.security.MessageDigest;

/**
 * Created by sadhu on 2017/2/20.
 * Email static.sadhu@gmail.com
 */
public class MD5Utils {

    public static String getMD5(String content) {
        String md5str = "";
        try {
            //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //2 将消息变成byte数组
            byte[] input = content.getBytes();
            //3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);
            //4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHexString(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
