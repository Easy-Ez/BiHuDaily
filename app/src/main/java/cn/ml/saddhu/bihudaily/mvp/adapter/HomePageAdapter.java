package cn.ml.saddhu.bihudaily.mvp.adapter;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.widget.CirclePageIndicator;

/**
 * Created by sadhu on 2016/11/26.
 * Email static.sadhu@gmail.com
 * Describe: 首页新闻列表适配器
 */
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int AUTO_NEXT_MILLIS = 5000;
    private static final int TYPE_LOOPER = 1;
    private static final int TYPE_NORMAL = 2;


    private StoryInfo info;
    private boolean hasLooper;
    private int viewPagerCurrentPosition;
    private OnItemClickListener mOnItemClickListener;

    public void addData(List<Story> datas) {
        int insertPosition = info.stories.size();
        info.stories.addAll(datas);
        notifyItemInserted(hasLooper ? insertPosition + 1 : insertPosition);
    }

    public void setData(StoryInfo info) {
        this.info = info;
        hasLooper = (info != null && info.topStories != null && info.topStories.size() > 0);
        viewPagerCurrentPosition = 0;
        notifyDataSetChanged();
    }

    public StoryInfo getData() {
        return this.info;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_LOOPER) {
            holder = new HomePageLooperVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_looper, parent, false));
        } else {
            holder = new HomePageNormalVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomePageLooperVH) {
            ((HomePageLooperVH) holder).bindData();
        } else if (holder instanceof HomePageNormalVH) {
            ((HomePageNormalVH) holder).bindData(info.stories.get(hasLooper ? position - 1 : position));
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (info != null) {
            if (hasLooper) {
                count += 1;
            }
            if (info.stories != null && info.stories.size() > 0) {
                count += info.stories.size();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasLooper) {
            return position == 0 ? TYPE_LOOPER : TYPE_NORMAL;
        } else {
            return TYPE_NORMAL;
        }
    }

    public boolean isHasLooper() {
        return hasLooper;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof HomePageLooperVH) {
            Logger.d("viewPagerCurrentPosition:" + viewPagerCurrentPosition);
            ((HomePageLooperVH) holder).release();
        }
        super.onViewRecycled(holder);
    }


    private class HomePageNormalVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout ll_tag;
        TextView tv_tag;
        TextView tv_title;
        SimpleDraweeView sdv_covers;
        ImageView img_mutil_tag;

        HomePageNormalVH(View itemView) {
            super(itemView);
            ll_tag = (LinearLayout) itemView.findViewById(R.id.ll_tag);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            sdv_covers = (SimpleDraweeView) itemView.findViewById(R.id.sdv_covers);
            img_mutil_tag = (ImageView) itemView.findViewById(R.id.img_mutil_tag);
            itemView.setOnClickListener(this);
        }

        void bindData(Story story) {
            ll_tag.setVisibility(story.isTag ? View.VISIBLE : View.GONE);
            if (story.isTag) {
                tv_tag.setText(story.tagName);
            }
            tv_title.setText(story.title);
            sdv_covers.setImageURI(story.images.get(0));
            img_mutil_tag.setVisibility(story.multipic ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onNormalItemClick(hasLooper ? getAdapterPosition() - 1 : getAdapterPosition());
            }
        }
    }

    private class HomePageLooperVH extends RecyclerView.ViewHolder {
        private final HomePageLooperAdapter adapter;
        private Handler mHandler;
        ViewPager looper;
        CirclePageIndicator indicator;
        GestureDetector tapGestureDetector;

        HomePageLooperVH(View itemView) {
            super(itemView);
            tapGestureDetector = new GestureDetector(itemView.getContext(), new TapGestureListener());
            looper = (ViewPager) itemView.findViewById(R.id.vp_looper);
            looper.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    viewPagerCurrentPosition = position;
                }
            });
            looper.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        Logger.d("onTouch down");
                        if (info.topStories.size() > 1) {
                            mHandler.removeCallbacksAndMessages(null);
                        }
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        Logger.d("onTouch up");
                        if (info.topStories.size() > 1) {
                            mHandler.postDelayed(new Task(), AUTO_NEXT_MILLIS);
                        }
                    }
                    tapGestureDetector.onTouchEvent(motionEvent);
                    return false;
                }
            });
            adapter = new HomePageLooperAdapter();
            looper.setAdapter(adapter);
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
            indicator.setViewPager(looper);
            mHandler = new Handler();
        }

        void bindData() {
            adapter.setData(info.topStories);
            indicator.setCurrentItem(viewPagerCurrentPosition);
            mHandler.removeCallbacksAndMessages(null);
            if (info.topStories.size() > 0) {
                mHandler.postDelayed(new Task(), AUTO_NEXT_MILLIS);
            }
        }

        void release() {
            mHandler.removeCallbacksAndMessages(null);
        }

        /**
         * 自动翻页
         */
        class Task implements Runnable {
            @Override
            public void run() {
                indicator.setCurrentItem((viewPagerCurrentPosition + 1) % info.topStories.size());
                mHandler.postDelayed(new Task(), AUTO_NEXT_MILLIS);
            }
        }

        /**
         * 监听轮播的单击事件
         */
        private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onLooperItemClick(viewPagerCurrentPosition % info.topStories.size());
                }
                return true;
            }
        }
    }


    /**
     * item点击回调
     */
    public interface OnItemClickListener {
        void onNormalItemClick(int position);

        void onLooperItemClick(int position);
    }
}
