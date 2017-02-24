package cn.ml.saddhu.bihudaily.engine.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */
public class ActivityUtils {

    public static void startActivityByUrl(Context context, String url) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
