package cn.ml.saddhu.bihudaily.engine.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.spec.AlgorithmParameterSpec;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sadhu on 2017/9/15.
 * 描述 匿名用户
 */
public class AuthUtil {

    private static final byte[] a = new byte[]{-125, 68, 122, -32, 28, 16, 38, -94, 26, 108, 95, -11, 31, -115, -73, -20};
    private static final byte[] b = new byte[]{82, -118, 106, 79, -112, 17, -26, -103, 3, -64, -40, -16, 10, -13, 105, 51};
    private static byte[] c = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};

    public static String getAnonymousData() {
        byte[] b = StringUtils.randomByte(16);
        AlgorithmParameterSpec b2 = b(b);
        long convert = TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        byte[] a = a(StringUtils.copyByteArray(StringUtils.copyByteArray(allocate.putInt(Long.valueOf(convert).intValue()).array(), StringUtils.randomByte(4)), c), b2);
        return Base64.encodeBytes(StringUtils.copyByteArray(StringUtils.copyByteArray(a(StringUtils.copyByteArray(b, a)), b), a));
    }

    private static byte[] a(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
            instance.init(1, new SecretKeySpec(b, "AES"), algorithmParameterSpec);
            return instance.doFinal(bArr);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] a(byte[] bArr) {
        try {
            Mac instance = Mac.getInstance("HmacMD5");
            instance.init(new SecretKeySpec(a, "HmacMD5"));
            return instance.doFinal(bArr);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static AlgorithmParameterSpec b(byte[] bArr) {
        try {
            return new IvParameterSpec(bArr);
        } catch (Throwable e) {
            return null;
        }
    }
}