package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.widget.customview.DragFrameLayout;

/**
 * Created by sadhu on 2017/2/27.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_test_drag)
public class TestActivity extends AppCompatActivity {

    int[] drawableRes = {R.drawable.android25,
            R.drawable.register_flow_chart,
            R.drawable.timg};

    @ViewById
    DragFrameLayout drag_view;

    @AfterViews
    public void afterViews() {
        MyAdapter myAdapter = new MyAdapter();

        drag_view.setPagerAdapter(myAdapter);
    }


    private class MyAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(drawableRes[position]);
            container.addView(imageView,
                    ViewPager.LayoutParams.WRAP_CONTENT,
                    ViewPager.LayoutParams.WRAP_CONTENT);

            return imageView;
        }

        @Override
        public int getCount() {
            return drawableRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
