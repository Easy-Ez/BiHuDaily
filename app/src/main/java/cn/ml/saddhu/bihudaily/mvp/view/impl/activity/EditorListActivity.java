package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnRecyclerViewItemClickListener;
import cn.ml.saddhu.bihudaily.engine.domain.Editor;
import cn.ml.saddhu.bihudaily.mvp.adapter.EditorListAdapter;
import cn.ml.saddhu.bihudaily.widget.decoration.MyLinearDividerDecoration;

/**
 * Created by sadhu on 2017/2/15.
 * Email static.sadhu@gmail.com
 * Describe: 主编列表
 */
@EActivity(R.layout.act_editor_list)
public class EditorListActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener<String> {
    @Extra
    String mEditors;
    @ViewById(R.id.toolbar_editor)
    Toolbar mToolbar;
    @ViewById(R.id.rv_editor_list)
    RecyclerView mRvEditorList;

    @AfterViews
    void afterViews() {
        Gson gson = new Gson();
        List<Editor> mEditorList = gson.fromJson(mEditors, new TypeToken<List<Editor>>() {
        }.getType());
        mRvEditorList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        EditorListAdapter editorListAdapter = new EditorListAdapter(mEditorList);
        mRvEditorList.setAdapter(editorListAdapter);
        mRvEditorList.addItemDecoration(new MyLinearDividerDecoration(this, 12, 12));
        editorListAdapter.setOnRecyclerViewItemClickListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String editorId, int position, int realPosition) {
        EditorActivity_.intent(this).mEditorId(editorId).start();
    }
}
