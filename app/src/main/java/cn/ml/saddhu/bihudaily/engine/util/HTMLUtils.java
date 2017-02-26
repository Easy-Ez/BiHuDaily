package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */
public class HTMLUtils {
    public static String parseBody(Context context, String body) {
        boolean isNoPicMode = SharePreferenceUtil.isNoPicMode(context);
        Document document = Jsoup.parse(body);
        for (Element next : document.getElementsByTag("img")) {
            String src = next.attr("src");
            next.attr("zhimg-src", src);
            String md5 = MD5Utils.getMD5(src);
            File file = new File(FileUtils.getStoryImageCacheFile(), md5 + 1);
            boolean inDiskCacheSync = file.exists();
            // 无图模式(非wifi 不显示图片)
            if (!isNoPicMode || !inDiskCacheSync) {
                next.attr("src", "file:///android_asset/default_pic_content_image_loading_light.png");
            } else {
                next.attr("src", "file:///android_asset/default_pic_content_image_download_light.png");
            }
            next.attr("onclick", "onImageClick(this)");
        }

        return document.html();
    }
}
