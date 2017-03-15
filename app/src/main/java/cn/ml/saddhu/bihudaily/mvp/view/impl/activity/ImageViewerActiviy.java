package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.imageloader.ImageDownloadManager;

/**
 * Created by sadhu on 2017/3/8.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_image_viewer)
public class ImageViewerActiviy extends BaseActivity implements ImageDownloadManager.GlobalDownloadListener {
    @ViewById
    Toolbar toolbar_imageviewer;
    @ViewById
    ProgressBar progressBar;
    @Extra
    String extra_image_url;

    @AfterViews
    void afterView() {
        if (ImageDownloadManager.getInstance().checkInTask(extra_image_url)) {
            ImageDownloadManager.getInstance().registerGlobalListener(extra_image_url, this);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.loadingbarColor), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        setSupportActionBar(toolbar_imageviewer);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.act_save_image) {
            Logger.i("保存");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(String localPath, String orginPath) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageDownloadManager.getInstance().unRegisterGlobalListener(extra_image_url);
    }
}
