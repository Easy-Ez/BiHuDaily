package cn.ml.saddhu.bihudaily.engine.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.ItemClickable;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnRecyclerViewItemClickListener;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;

/**
 * Created by sadhu on 2017/2/17.
 * Email static.sadhu@gmail.com
 * 主编列表_adapter
 */
public class EditorListAdapter extends RecyclerView.Adapter<EditorListAdapter.EditorListVH> implements ItemClickable<String> {

    private List<Editor> mEditorList;
    private OnRecyclerViewItemClickListener<String> mItemClickListener;

    public EditorListAdapter(List<Editor> mEditorList) {
        this.mEditorList = mEditorList;
    }

    @Override
    public EditorListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditorListVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editor, parent, false));
    }

    @Override
    public void onBindViewHolder(EditorListVH holder, int position) {
        holder.setData(mEditorList.get(position));

    }

    @Override
    public int getItemCount() {
        return mEditorList.size();
    }

    @Override
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<String> listener) {
        this.mItemClickListener = listener;
    }

    class EditorListVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView mSdvAvatar;
        TextView mTvName;
        TextView mTvTitle;
        TextView mTvOrganization;

        public EditorListVH(View itemView) {
            super(itemView);
            mSdvAvatar = (SimpleDraweeView) itemView.findViewById(R.id.theme_editor_sdv);
            mTvName = (TextView) itemView.findViewById(R.id.theme_editor_name);
            mTvTitle = (TextView) itemView.findViewById(R.id.theme_editor_title);
            mTvOrganization = (TextView) itemView.findViewById(R.id.theme_editor_bio);
            itemView.setOnClickListener(this);
        }

        public void setData(Editor editor) {
            mSdvAvatar.setImageURI(editor.avatar);
            if (TextUtils.isEmpty(editor.title)) {
                mTvTitle.setVisibility(View.GONE);
            } else {
                mTvTitle.setVisibility(View.VISIBLE);
                mTvTitle.setText(editor.title);
            }
            mTvName.setText(editor.name);
            mTvOrganization.setText(editor.bio);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(String.valueOf(mEditorList.get(getAdapterPosition()).id),
                        getAdapterPosition(),
                        getAdapterPosition());
            }
        }
    }
}
