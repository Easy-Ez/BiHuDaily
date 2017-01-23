package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2016/12/5.
 * Email static.sadhu@gmail.com
 * Describe: 详情页
 */
@EActivity(R.layout.act_stroy_detail)
public class StroyDetailActivity extends AppCompatActivity {
    @ViewById
    Toolbar toolbar;
    @ViewById
    ViewPager vp_stroy_detail;

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stroy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_favorite:
                return true;
            case R.id.action_comment:
                return true;
            case R.id.action_vote:
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
