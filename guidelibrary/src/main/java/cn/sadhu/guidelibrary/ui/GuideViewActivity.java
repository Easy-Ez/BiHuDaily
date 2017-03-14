package cn.sadhu.guidelibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.sadhu.guidelibrary.R;

/**
 * Created by sadhu on 2017/3/14.
 * Email static.sadhu@gmail.com
 */
public class GuideViewActivity extends AppCompatActivity {
    public static final String EXTRA_LIST = "extra_target_view_infos";
    FrameLayout fl_container;

    private ArrayList<ArrayList<GuideViewInfo>> mLists;
    private int mCurrentStep = 0;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLists = (ArrayList<ArrayList<GuideViewInfo>>) getIntent().getSerializableExtra(GuideViewActivity.EXTRA_LIST);
        if (mLists == null) {
            finish();
        } else {
            setContentView(R.layout.act_guide_view);
            fl_container = (FrameLayout) findViewById(R.id.fl_container);
            fl_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLists.size() > 1 && mCurrentStep < mLists.size()) {
                        addGuideView(mLists.get(mCurrentStep));
                    } else {
                        finish();
                    }
                }
            });
            addGuideView(mLists.get(mCurrentStep));
        }
    }


    private void addGuideView(List<GuideViewInfo> guideViewInfos) {
        fl_container.removeAllViews();
        for (GuideViewInfo info : guideViewInfos) {
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
