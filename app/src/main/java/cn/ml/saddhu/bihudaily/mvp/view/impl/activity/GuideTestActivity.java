package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.sadhu.guidelibrary.domain.TargetViewInfo;
import cn.sadhu.guidelibrary.ui.GuideViewManager;
import cn.sadhu.guidelibrary.util.UIUtils;

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
        img_my_mall.post(new Runnable() {
            @Override
            public void run() {
                Rect viewOnScreenRect = UIUtils.getViewOnScreenRect(img_my_mall);
                Logger.i("left:" + viewOnScreenRect.left + ";left:" + viewOnScreenRect.bottom);
                new GuideViewManager.Builder(GuideTestActivity.this)
                        .addTargetView(new TargetViewInfo().setResource(R.drawable.guide1).setMarginTop(20).setMarginLeft(20))
                        .addTargetView(new TargetViewInfo().setResource(R.drawable.guide2).setMarginTop(500).setMarginLeft(20))
                        .setBackgroundColor(Color.parseColor("#33ff0000"))
                        .build()
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
