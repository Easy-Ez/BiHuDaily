package cn.sadhu.guidelibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import cn.sadhu.guidelibrary.R;
import cn.sadhu.guidelibrary.domain.GuideViewInfo;
import cn.sadhu.guidelibrary.domain.TargetViewInfo;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
public class GuideViewActivity extends AppCompatActivity {
    public static final String EXTRA_LIST = "extra_target_view_infos";
    FrameLayout fl_container;

    private GuideViewInfo mGuideViewInfo;
    private int mCurrentStep = 0;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGuideViewInfo = (GuideViewInfo) getIntent().getSerializableExtra(GuideViewActivity.EXTRA_LIST);
        if (mGuideViewInfo != null && mGuideViewInfo.mLists != null) {
            setContentView(R.layout.act_guide_view);
            fl_container = (FrameLayout) findViewById(R.id.fl_container);
            fl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGuideViewInfo.mLists.size() > 1 && mCurrentStep < mGuideViewInfo.mLists.size()) {
                        addGuideView(mGuideViewInfo.mLists.get(mCurrentStep));
                    } else {
                        finish();
                    }
                }
            });
            fl_container.setBackgroundColor(mGuideViewInfo.mBackgroundColor);
            addGuideView(mGuideViewInfo.mLists.get(mCurrentStep));
        } else {
            throw new RuntimeException("data is null");
        }
    }


    private void addGuideView(List<TargetViewInfo> targetViewInfos) {
        fl_container.removeAllViews();
        for (TargetViewInfo info : targetViewInfos) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(info.marginLeft, info.marginTop, info.marginRight, info.marginBottom);
            params.gravity = info.gravity;
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(info.resource);
            fl_container.addView(imageView, params);
        }
        mCurrentStep++;
    }
}
