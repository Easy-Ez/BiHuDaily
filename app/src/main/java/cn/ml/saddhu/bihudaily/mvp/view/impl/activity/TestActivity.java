package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.widget.StoryWebView;

/**
 * Created by sadhu on 2017/2/27.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_test)
public class TestActivity extends AppCompatActivity {
    @ViewById(R.id.wb)
    StoryWebView wb;

    @AfterViews
    public void afterViews() {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("html2.html")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader == null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        wb.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html", "UTF-8", null);
    }
}
