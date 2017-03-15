package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.mvp.view.impl.fragment.SettingsFragment;

/**
 * Created by sadhu on 2017/2/27.
 * Email static.sadhu@gmail.com
 * Describe: 设置界面
 * 这里要特殊处理back按钮
 */
@EActivity(R.layout.act_settings)
public class SettingsActivity extends AppCompatActivity {
    @ViewById(R.id.settings_toolbar)
    Toolbar mToolbars;

    @AfterViews
    public void afterViews() {
        setSupportActionBar(mToolbars);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_content, new SettingsFragment())
                .commit();
    }
}
