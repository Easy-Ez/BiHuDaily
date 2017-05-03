package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.sadhu.guidelibrary.domain.TargetViewInfo;
import cn.sadhu.guidelibrary.ui.GuideViewManager;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_guide_test)
public class GuideTestActivity extends BaseActivity {

    @ViewById
    ImageView img_my_mall;
    @ViewById
    ImageView img_my_studio;

    @AfterViews
    void afterViews() {
        new GuideViewManager.Builder(this)
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide1).setMarginTop(20).setMarginLeft(20))
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide2).setMarginTop(500).setMarginLeft(20))
                .switchNextSetp()
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide3).setMarginTop(100).setMarginLeft(20))
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide4).setMarginTop(300).setMarginLeft(20))
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide5).setMarginTop(600).setMarginLeft(20))
                .switchNextSetp()
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide1).setGravity(Gravity.END).setMarginTop(100).setMarginRight(20))
                .addTargetView(new TargetViewInfo().setResource(R.drawable.guide2).setMarginTop(500).setMarginLeft(20))
                .setIsFullMode(true)
                .setWithAnimation(true)
                .setBackgroundColor(Color.parseColor("#55000000"))
                .build()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
