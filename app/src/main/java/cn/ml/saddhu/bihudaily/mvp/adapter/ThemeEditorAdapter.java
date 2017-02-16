package cn.ml.saddhu.bihudaily.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;

/**
 * Created by sadhu on 2017/2/12.
 * Email static.sadhu@gmail.com
 * Describe: 主题_主编列表adapter
 */
public class ThemeEditorAdapter extends RecyclerView.Adapter<ThemeEditorAdapter.ThemeEditorVH> {
    private List<Editor> mEditors = new ArrayList<>();

    @Override

    public ThemeEditorVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThemeEditorVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_header_editor, parent, false));
    }

    @Override
    public void onBindViewHolder(ThemeEditorVH holder, int position) {
        holder.setAvatarUrl(mEditors.get(position).avatar);
    }

    @Override
    public int getItemCount() {
        return mEditors == null ? 0 : mEditors.size();
    }

    public void setData(List<Editor> editors) {
        if (mEditors != editors) {
            mEditors = editors;
            notifyDataSetChanged();
        }
    }

    class ThemeEditorVH extends RecyclerView.ViewHolder {
        SimpleDraweeView mSdvAvatar;

        ThemeEditorVH(View itemView) {
            super(itemView);
            mSdvAvatar = (SimpleDraweeView) itemView.findViewById(R.id.sdv_avatar);
        }

        void setAvatarUrl(String url) {
            mSdvAvatar.setImageURI(url);
        }
    }
}
