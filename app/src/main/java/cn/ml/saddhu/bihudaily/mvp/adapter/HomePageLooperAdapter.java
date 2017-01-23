package cn.ml.saddhu.bihudaily.mvp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.TopStory;

/**
 * Created by sadhu on 2016/11/30.
 * Email static.sadhu@gmail.com
 * Describe: 轮播图适配器
 */
public class HomePageLooperAdapter extends PagerAdapter {
    private List<TopStory> topStories;

    public void setData(List<TopStory> topStories) {
        this.topStories = topStories;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return topStories != null ? topStories.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_home_looper_item, container, false);
        SimpleDraweeView sdv_covers = (SimpleDraweeView) view.findViewById(R.id.sdv_looper_cover);
        TextView tv_looper_title = (TextView) view.findViewById(R.id.tv_looper_title);
        sdv_covers.setImageURI(topStories.get(position).getImage());
        tv_looper_title.setText(topStories.get(position).getTitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
