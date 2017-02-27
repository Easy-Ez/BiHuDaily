package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnToolBarNeedChangeListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.engine.util.StringUtils;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryActDetailPresetner;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.StoryActDetailPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.StoryDetailActView;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.StoryDetailFragment_;

/**
 * Created by sadhu on 2016/12/5.
 * Email static.sadhu@gmail.com
 * Describe: 详情页
 */
@EActivity(R.layout.act_story_detail)
public class StoryDetailActivity extends AppCompatActivity implements StoryDetailActView, OnToolBarNeedChangeListener, View.OnClickListener {
    @ViewById
    Toolbar toolbar;
    @ViewById
    ViewPager vp_stroy_detail;
    @Extra
    ArrayList<String> mIdLists;
    @Extra
    int mPosition;

    private StoryDetailExtra mExtra;
    private StoryActDetailPresetner mPresetner;

    @AfterViews
    void afterViews() {
        mPresetner = new StoryActDetailPresenterImpl(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        vp_stroy_detail.setOffscreenPageLimit(1);
        vp_stroy_detail.setAdapter(new MyPagerFragmentAdapter(getSupportFragmentManager()));
        vp_stroy_detail.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPresetner.getStoryDetailExtra(mIdLists.get(position));
            }
        });
        vp_stroy_detail.setCurrentItem(mPosition, true);
        if (mPosition == 0) {
            mPresetner.getStoryDetailExtra(mIdLists.get(mPosition));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stroy, menu);
        menu.findItem(R.id.action_comment).getActionView().setOnClickListener(this);
        menu.findItem(R.id.action_vote).getActionView().setOnClickListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Logger.i("action_share");
                StoryDetailExtra storyDetailExtra = new StoryDetailExtra();
                storyDetailExtra.comments = 145;
                storyDetailExtra.popularity = 2500;
                setToolBarInfo(storyDetailExtra);
                return true;
            case R.id.action_favorite:
                Logger.i("action_favorite");
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_menu_comment) {
            Logger.i("action_comment");
        } else if (v.getId() == R.id.tv_menu_vote) {
            Logger.i("action_vote");
        }
    }

    @Override
    public void setToolBarInfo(StoryDetailExtra extra) {
        mExtra = extra;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mExtra != null) {
            ((TextView) menu.findItem(R.id.action_comment).getActionView())
                    .setText(StringUtils.integer2StringWithThousand(mExtra.comments));
            ((TextView) menu.findItem(R.id.action_vote).getActionView())
                    .setText(StringUtils.integer2StringWithThousand(mExtra.popularity));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setToolBarAlpha(@FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        toolbar.setAlpha(alpha);
    }

    @Override
    public void hideToolbar(boolean hide) {
        toolbar.setVisibility(hide ? View.GONE : View.VISIBLE);
    }


    class MyPagerFragmentAdapter extends FragmentStatePagerAdapter {

        public MyPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return StoryDetailFragment_
                    .builder()
                    .mStoryId(mIdLists.get(position))
                    .build();
        }

        @Override
        public int getCount() {
            return mIdLists.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresetner.onDestroy();
    }
}
