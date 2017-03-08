package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.domain.Theme;
import cn.ml.saddhu.bihudaily.engine.util.SharePreferenceUtil;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.NavigationDrawerFragment;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.StoryListFragment;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.StoryListFragment_;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.ThemeListFragment;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.ThemeListFragment_;

@EActivity(R.layout.act_main)
public class MainActivity extends AppCompatActivity implements StoryListFragment.OnToolBarTitleChangeListener {

    private static final String STATE_KEY_FRAGMENT = "keyStoryFragment";
    @ViewById
    Toolbar toolbar;
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById
    FrameLayout fl_conent;
    @FragmentById(R.id.navigation_drawer)
    NavigationDrawerFragment mDrawerFrag;
    StoryListFragment mStoryListFragment;
    ThemeListFragment mThemeListFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            mStoryListFragment = (StoryListFragment) manager.getFragment(savedInstanceState, STATE_KEY_FRAGMENT);
        } else {
            mStoryListFragment = StoryListFragment_.builder().build();
        }
    }


    @AfterViews
    void afterViews() {
        toolbar.setTitle(R.string.title_home);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mDrawerFrag != null) {
                    // 这里只处理actionView
                    Theme theme = mDrawerFrag.getCurrentTheme();
                    if (theme != null) {
                        MenuItem item = toolbar.getMenu().findItem(R.id.action_theme_edit);
                        if (item != null) {
                            item.setIcon(theme.isSubscribe
                                    ? R.drawable.action_remove
                                    : R.drawable.action_add);
                            item.setTitle(theme.isSubscribe
                                    ? getString(R.string.remove_subscribe)
                                    : getString(R.string.subscribe));
                        }
                    }
                }

            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment storyListFragment = supportFragmentManager.findFragmentByTag("StoryListFragment");
        if (storyListFragment == null) {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.add(R.id.fl_conent, mStoryListFragment, "StoryListFragment");
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_theme);
        boolean isDark = SharePreferenceUtil.isDark(this);
        item.setTitle(isDark ? getString(R.string.light_mode) : getString(R.string.dark_mode));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // 设置选项
            SettingsActivity_.intent(this).start();
            return true;
        } else if (id == R.id.action_message) {
            // 消息 暂时用来做测试
            TestActivity_.intent(this).start();
            return true;
        } else if (id == R.id.action_theme) {
            // 切换主题
            boolean isDark = toggleThemeSetting();
            item.setTitle(isDark ? getString(R.string.light_mode) : getString(R.string.dark_mode));
            return true;
        } else if (id == R.id.action_theme_edit) {
            Toast.makeText(this, "订阅/取消订阅", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTitleChange(String tagName) {
        if (!toolbar.getTitle().equals(tagName)) {
            toolbar.setTitle(tagName);
        }
    }

    /**
     * 切换主题
     *
     * @return true:夜间模式 false: 日间模式
     */
    private boolean toggleThemeSetting() {
        boolean isDark = false;
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                isDark = false;
                SharePreferenceUtil.setThemeMode(this, SharePreferenceUtil.THEME_MODE_LIGHT);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                isDark = true;
                SharePreferenceUtil.setThemeMode(this, SharePreferenceUtil.THEME_MODE_DARK);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        //fixme Nougat not showing animation
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
        return isDark;
    }

    /**
     * 主题被选中
     *
     * @param theme
     */
    public void selectDrawerItem(Theme theme, boolean isDifItem) {
        closeDrawer();
        if (isDifItem) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            if (theme == null) {
                toolbar.setTitle(R.string.title_home);
                toolbar.getMenu().clear();
                getMenuInflater().inflate(R.menu.main, toolbar.getMenu());
                fragmentTransaction.show(mStoryListFragment);
                if (mThemeListFragment != null) {
                    fragmentTransaction.hide(mThemeListFragment);
                }
                if (mStoryListFragment != null) {
                    mStoryListFragment.getHomePageList();
                }
            } else {
                toolbar.setTitle(theme.name);
                toolbar.getMenu().clear();
                getMenuInflater().inflate(R.menu.theme, toolbar.getMenu());
                MenuItem item = toolbar.getMenu().findItem(R.id.action_theme_edit);
                item.setIcon(theme.isSubscribe
                        ? R.drawable.action_remove
                        : R.drawable.action_add);
                item.setTitle(theme.isSubscribe
                        ? getString(R.string.remove_subscribe)
                        : getString(R.string.subscribe));
                if (mThemeListFragment == null) {
                    mThemeListFragment = ThemeListFragment_.builder().mThemeId(String.valueOf(theme.id)).build();
                } else {
                    mThemeListFragment.setThemeId(String.valueOf(theme.id));
                }
                fragmentTransaction.hide(mStoryListFragment);
                if (!mThemeListFragment.isAdded()) {
                    fragmentTransaction.add(R.id.fl_conent, mThemeListFragment, "ThemeListFragment");
                } else {
                    fragmentTransaction.show(mThemeListFragment);
                }
            }
            fragmentTransaction.commit();
        }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        manager.putFragment(outState, STATE_KEY_FRAGMENT, mStoryListFragment);
    }

}
