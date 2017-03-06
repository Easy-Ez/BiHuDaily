package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.widget.customview.ExpandableTextView;

/**
 * Created by sadhu on 2017/2/27.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_test)
public class TestActivity extends AppCompatActivity {
    @ViewById
    ExpandableTextView expandable_tv;
    private String mString1;
    private String mString2;
    private String mString3;

    @AfterViews
    public void afterViews() {


    }

    @Click(R.id.btn_click)
    void onToggleClicked() {
        expandable_tv.toggle();
    }

    @Click(R.id.btn_change)
    void onChangeClicked() {
        Random random = new Random();
        String name = "test_str" + (random.nextInt(3) + 1);
        Logger.i("identifier:" + name);
        expandable_tv.setText(getResources().getIdentifier(name, "string", getPackageName()));
    }

}
