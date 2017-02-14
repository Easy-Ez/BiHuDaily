package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/15.
 * Email static.sadhu@gmail.com
 * Describe: 主编列表
 */
@EActivity(R.layout.act_editor_list)
public class EditorListActivity extends AppCompatActivity {
    @ViewById(R.id.ll_content)
    LinearLayout mLlContent;
    @ViewById(R.id.rv_editor_list)
    RecyclerView mRvEditorList;

    @AfterViews
    void afterViews() {
        mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("onclick");
            }
        });
        mRvEditorList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRvEditorList.setAdapter(new MyAdapter());
        mRvEditorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("onclick");
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyVH> {
        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editor, parent, false));
        }

        @Override
        public void onBindViewHolder(MyVH holder, int position) {
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    class MyVH extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvOrganization;

        public MyVH(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_editor_name);
            mTvOrganization = (TextView) itemView.findViewById(R.id.tv_editor_organization);
        }

        public void setData(int position) {
            mTvName.setText("xxxx" + position);
            mTvOrganization.setText("cccccccccccc" + position);
        }
    }
}
