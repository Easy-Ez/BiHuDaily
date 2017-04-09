package cn.ml.saddhu.bihudaily.engine.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.BaseStory;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;
import cn.ml.saddhu.bihudaily.engine.domain.ThemeInfo;
import cn.ml.saddhu.bihudaily.engine.imageloader.ImageLoader;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主题列表适配器
 */
public class ThemePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_NORMAL = 2;
    private ThemeInfo mThemeInfo;
    private OnItemClickListener mListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == TYPE_HEADER) {
            vh = new ThemeHeaderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_header, parent, false));
        } else {
            vh = new ThemeNormalVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_normal, parent, false));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThemeHeaderVH) {
            ((ThemeHeaderVH) holder).setHeaderData(mThemeInfo.background, mThemeInfo.description, mThemeInfo.editors);
        } else if (holder instanceof ThemeNormalVH) {
            ((ThemeNormalVH) holder).setThemeItemData(mThemeInfo.stories.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        if (mThemeInfo == null || mThemeInfo.stories == null) {
            return 0;
        } else {
            return mThemeInfo.stories.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setData(ThemeInfo info) {
        this.mThemeInfo = info;
        notifyDataSetChanged();
    }

    public void addData(List<BaseStory> stories) {
        int insertPosition = mThemeInfo.stories.size();
        mThemeInfo.stories.addAll(stories);
        notifyItemInserted(insertPosition + 1);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public ThemeInfo getData() {
        return mThemeInfo;
    }

    private class ThemeHeaderVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView mSdvCover;
        ImageView mImgAutomatic;
        TextView mTvThemeTitle;
        RecyclerView mRvThemeList;
        LinearLayout mLlEditor;
        ThemeEditorAdapter mThemeEditorAdapter;


        ThemeHeaderVH(View itemView) {
            super(itemView);
            mSdvCover = (SimpleDraweeView) itemView.findViewById(R.id.sdv_cover);
            mImgAutomatic = (ImageView) itemView.findViewById(R.id.img_automatic);
            mTvThemeTitle = (TextView) itemView.findViewById(R.id.tv_theme_title);
            mRvThemeList = (RecyclerView) itemView.findViewById(R.id.theme_editor_list);
            mRvThemeList.getParent().requestDisallowInterceptTouchEvent(false);
            // 使用InterceptLinearLayout 或者 setLayoutFrozen的办法 阻止recyclerview抢夺touchEvent
            mLlEditor = (LinearLayout) itemView.findViewById(R.id.theme_editor_top_layout);
            mLlEditor.setOnClickListener(this);
            mRvThemeList.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            mThemeEditorAdapter = new ThemeEditorAdapter();
            mRvThemeList.setAdapter(mThemeEditorAdapter);
            //mRvThemeList.setLayoutFrozen(true);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onEditorItemClick(mThemeInfo.editors);
            }
        }

        void setHeaderData(String background, String description, List<Editor> editors) {
//            int width = 768 / 2, height = 648 / 2;
//            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(background))
//                    .setResizeOptions(new ResizeOptions(width, height))
//                    .build();
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setOldController(mSdvCover.getController())
//                    .setImageRequest(request)
//                    .build();
            //mSdvCover.setController(controller);
            mTvThemeTitle.setText(description);
            //mRvThemeList.setLayoutFrozen(false);
            mThemeEditorAdapter.setData(editors);
            //mRvThemeList.setLayoutFrozen(true);

            ImageLoader.getInstance().loadImage(mImgAutomatic, background);
        }
    }

    private class ThemeNormalVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvTitle;
        SimpleDraweeView mSdvCovers;

        ThemeNormalVH(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mSdvCovers = (SimpleDraweeView) itemView.findViewById(R.id.sdv_covers);
            itemView.setOnClickListener(this);
        }

        void setThemeItemData(BaseStory story) {
            if (story.images == null || story.images.size() == 0) {
                mSdvCovers.setVisibility(View.GONE);
            } else {
                mSdvCovers.setVisibility(View.VISIBLE);
                mSdvCovers.setImageURI(story.images.get(0));
            }
            mTvTitle.setText(story.title);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onNormalItemClick( getAdapterPosition() - 1);
            }
        }
    }


    /**
     * item点击回调
     */
    public interface OnItemClickListener {
        void onEditorItemClick(List<Editor> editors);

        void onNormalItemClick(int position);
    }
}
