package cn.ml.saddhu.bihudaily.engine.encrypt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sadhu on 2017/3/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */

public class encrypt {


    private static final byte[] a;
    private static final byte[] b;
    private static byte[] c;

    static {
        a = new byte[]{-125, 68, 122, -32, 28, 16, 38, -94, 26, 108, 95, -11, 31, -115, -73, -20};
        b = new byte[]{82, -118, 106, 79, -112, 17, -26, -103, 3, -64, -40, -16, 10, -13, 105, 51};
        c = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public static String generateAnonymousData() {
        byte[] v0 = af.b(16);
        AlgorithmParameterSpec v1 = b2(v0);
        long v2 = TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        ByteBuffer v4 = ByteBuffer.allocate(4);
        v4.order(ByteOrder.LITTLE_ENDIAN);
        byte[] v1_1 = a(af.a(af.a(v4.putInt(Long.valueOf(v2).intValue()).array(), af.b(4)), c), v1);
        return A.a(af.a(af.a(a(af.a(v0, v1_1)), v0), v1_1));
    }


    private static byte[] a(byte[] arg5, AlgorithmParameterSpec arg6) {
        try {
            Cipher v0_1 = Cipher.getInstance("AES/CBC/NoPadding");
            v0_1.init(1, new SecretKeySpec(b, "AES"), arg6);
            return v0_1.doFinal(arg5);
        } catch (Exception v0) {
            throw new RuntimeException(((Throwable) v0));
        }
    }

    private static byte[] a(byte[] arg4) {
        try {
            Mac v0_2 = Mac.getInstance("HmacMD5");
            v0_2.init(new SecretKeySpec(a, "HmacMD5"));
            byte[] v0_3 = v0_2.doFinal(arg4);
            return v0_3;
        } catch (InvalidKeyException v0) {
        } catch (NoSuchAlgorithmException v0_1) {
        }

        return null;
    }

    public static byte[] b(int paramInt) {
        byte[] arrayOfByte = new byte[paramInt];
        new Random().nextBytes(arrayOfByte);
        return arrayOfByte;
    }

    private static AlgorithmParameterSpec b2(byte[] arg1) {
        AlgorithmParameterSpec v0_2;
        try {
            v0_2 = new IvParameterSpec(arg1);
        } catch (Exception v0) {
            v0_2 = null;
        }

        return v0_2;
    }
}
