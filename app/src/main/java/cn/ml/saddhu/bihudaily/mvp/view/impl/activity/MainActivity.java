package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.StoryListFragment;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.StoryListFragment_;
import cn.sadhu.daynightlibrary.utils.DayNightSpUtil;

@EActivity(R.layout.act_main)
public class MainActivity extends AppCompatActivity implements StoryListFragment.OnToolBarTitleChangeListener {

    @ViewById
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById
    FrameLayout fl_conent;
    private StoryListFragment mStoryListFragment;

    @AfterViews
    void afterViews() {
        toolbar.setTitle(R.string.title_home);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mStoryListFragment = StoryListFragment_.builder().build();
        transaction.add(R.id.fl_conent, mStoryListFragment, "StoryListFragment");
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // 设置选项
            return true;
        } else if (id == R.id.action_message) {
            // 消息
            return true;
        } else if (id == R.id.action_theme) {
            // 切换主题
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 关闭左侧导航
     *
     * @return
     */
    private boolean closeDrawer() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    /**
     * 主题被选中
     *
     * @param theme
     */
    public void selectDrawerItem(Theme theme) {
        closeDrawer();
        if (theme == null) {
            toolbar.setTitle(R.string.title_home);
            if (mStoryListFragment != null) {
                mStoryListFragment.getHomePageList();
            }
        } else {
            toolbar.setTitle(theme.name);
            if (mStoryListFragment != null) {
                mStoryListFragment.getThemePageList(theme);
            }
            Logger.d(theme);
        }
    }


    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTitleChange(String tagName) {
        if (!toolbar.getTitle().equals(tagName)) {
            toolbar.setTitle(tagName);
        }
    }
}
