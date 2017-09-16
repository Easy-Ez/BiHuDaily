package cn.ml.saddhu.bihudaily.engine.util;

import java.text.NumberFormat;
import java.util.Random;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe: 字符串工具类
 */
public class StringUtils {
    public static String integer2StringWithThousand(int num) {
        String result = String.valueOf(num);
        if (num >= 1000) {
            NumberFormat instance = NumberFormat.getInstance();
            instance.setMaximumFractionDigits(1);
            result = instance.format(num / 1000.0) + "K";
        }
        return result;
    }


    public static byte[] copyByteArray(byte[] bytes1, byte[] bytes2) {
        byte[] bytes3 = new byte[(bytes1.length + bytes2.length)];

        System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
        System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);

        int i = 0;
        while (i < bytes3.length) {
            bytes3[i] = i < bytes1.length ? bytes1[i] : bytes2[i - bytes1.length];
            i++;
        }
        return bytes3;
    }

    public static byte[] randomByte(int i) {
        byte[] bArr = new byte[i];
        new Random().nextBytes(bArr);
        return bArr;
    }

}
