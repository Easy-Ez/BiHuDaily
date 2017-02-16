package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;

/**
 * Created by sadhu on 2017/2/15.
 * Email static.sadhu@gmail.com
 * Describe: 主编列表
 */
@EActivity(R.layout.act_editor_list)
public class EditorActivity extends AppCompatActivity {
    @Extra
    String mEditors;
    @ViewById(R.id.ll_content)
    LinearLayout mLlContent;
    @ViewById(R.id.rv_editor_list)
    RecyclerView mRvEditorList;

    private List<Editor> mEditorList;

    @AfterViews
    void afterViews() {
        Gson gson = new Gson();
        mEditorList = gson.fromJson(mEditors, new TypeToken<List<Editor>>() {
        }.getType());
        mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("onclick");
            }
        });
        mRvEditorList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvEditorList.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyVH> {
        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editor, parent, false));
        }

        @Override
        public void onBindViewHolder(MyVH holder, int position) {
            holder.setData(mEditorList.get(position));
        }

        @Override
        public int getItemCount() {
            return mEditorList.size();
        }
    }

    class MyVH extends RecyclerView.ViewHolder {
        SimpleDraweeView mSdvAvatar;
        TextView mTvName;
        TextView mTvTitle;
        TextView mTvOrganization;

        public MyVH(View itemView) {
            super(itemView);
            mSdvAvatar = (SimpleDraweeView) itemView.findViewById(R.id.theme_editor_sdv);
            mTvName = (TextView) itemView.findViewById(R.id.theme_editor_name);
            mTvTitle = (TextView) itemView.findViewById(R.id.theme_editor_title);
            mTvOrganization = (TextView) itemView.findViewById(R.id.theme_editor_bio);
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
    }
}
