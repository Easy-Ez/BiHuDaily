package cn.ml.saddhu.bihudaily.engine.encrypt;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by sadhu on 2017/3/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */

public class af {
    protected static final char[] a;

    static {
        a = "0123456789ABCDEF".toCharArray();
    }

    public static byte[] a(byte[] arg3, byte[] arg4) {
        byte[] v2 = new byte[arg3.length + arg4.length];
        int v0;
        for (v0 = 0; v0 < v2.length; ++v0) {
            byte v1 = v0 < arg3.length ? arg3[v0] : arg4[v0 - arg3.length];
            v2[v0] = v1;
        }

        return v2;
    }

    public static String a(int arg6) {
        String v0 = arg6 < 1000 ? String.valueOf(arg6) : new DecimalFormat("0.0K").format((((double) (arg6 / 100))) / 10);
        return v0;
    }

    public static byte[] b(int arg2) {
        byte[] v0 = new byte[arg2];
        new Random().nextBytes(v0);
        return v0;
    }
}