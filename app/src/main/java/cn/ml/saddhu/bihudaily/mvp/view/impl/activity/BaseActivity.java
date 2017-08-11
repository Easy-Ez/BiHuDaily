package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.ml.saddhu.bihudaily.mvp.view.IBaseView;

/**
 * Created by sadhu on 2016/11/12.
 * Email static.sadhu@gmail.com
 * Describe: activity 基类
 */
public class BaseActivity extends AppCompatActivity implements IBaseView {


    protected final void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected final void showToast(String msg, int duration) {
        Toast.makeText(this, msg, duration).show();
    }


    @Override
    public Context getContext() {
        return this;
    }
}
