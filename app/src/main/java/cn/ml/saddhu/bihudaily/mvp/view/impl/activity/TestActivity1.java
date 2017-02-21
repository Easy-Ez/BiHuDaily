package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.TestFragment_;

/**
 * Created by sadhu on 2017/2/21.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_test1)
public class TestActivity1 extends AppCompatActivity {
    @ViewById(R.id.vp)
    ViewPager mVp;


    @AfterViews
    void afterViews() {
        mVp.setAdapter(new MyPagerFragment(getSupportFragmentManager()));
    }


    class MyPagerFragment extends FragmentPagerAdapter {

        public MyPagerFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment_.builder().build();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
