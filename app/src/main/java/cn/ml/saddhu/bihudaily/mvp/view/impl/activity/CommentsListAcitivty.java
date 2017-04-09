package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v4.app.NavUtils;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.mvp.presenter.CommentsPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.CommentsPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.CommentsListView;

/**
 * Created by sadhu on 2017/3/27.
 * Email static.sadhu@gmail.com
 * Describe: 评论列表
 */
@EActivity(R.layout.act_comments_list)
public class CommentsListAcitivty extends BaseActivity implements CommentsListView {
    @Extra
    StoryDetailExtra mExtra;
    @Extra
    String mStoryId;

    @ViewById
    Toolbar toolbar_comments;
    @ViewById
    RecyclerView rv_comments_list;
    private LinearLayoutManager mLinearLayoutManager;
    private CommentsPresenter mPresenter;
    private RecyclerView.Adapter mCommentAdapter;

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar_comments);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.comment_count, mExtra.comments));
        }
        mPresenter = new CommentsPresenterImpl(this);
        mPresenter.setCommentsNum(mExtra.short_comments, mExtra.long_comments);
        mPresenter.setStoryId(mStoryId);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mCommentAdapter = mPresenter.getAdapter();
        rv_comments_list.setLayoutManager(mLinearLayoutManager);
        rv_comments_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv_comments_list.setAdapter(mCommentAdapter);
        mPresenter.getLongCommentsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_comments) {
            Logger.i("写评论");
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefreshSucces(List<CommentBean> data) {

    }

    @Override
    public void onRefreshError(int code) {

    }

    @Override
    public void onLoadMoreSuccuess(List<CommentBean> data) {

    }


    @Override
    public void onLoadMoreError(int code) {

    }
}
