package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;
import android.graphics.drawable.shapes.Shape;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */
public class HTMLUtils {
    public static String parseBody(Context context, String body) {
        LinkedList v2 = new LinkedList();
        boolean v3 = SharePreferenceUtil.isNoPicMode(context);
        Document v4 = Jsoup.parse(body);
        Iterator<Element> v5 = v4.getElementsByTag("img").iterator();
        while (v5.hasNext()) {
            Element v0 = v5.next();
            String v6 = v0.attr("src");
            v0.attr("zhimg-src", v6);
            File v1 = com.c.a.b.d.a().c().a(v6);
            if (v1 == null || !v1.exists()) {
                v1 = null;
            }

            int v1_1 = v1 != null ? 1 : 0;
            if (!v3 || v1_1 != 0) {
                ((Element) v0).attr("src", "file:///android_asset/default_pic_content_image_loading_light.png");
            } else {
                ((Element) v0).attr("src", "file:///android_asset/default_pic_content_image_download_light.png");
            }

            ((Element) v0).attr("onclick", "onImageClick(this)");
            v2.add(v6);
        }

        return v4.html();
    }
}
