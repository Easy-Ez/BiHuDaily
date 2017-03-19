package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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

import java.io.File;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.imageloader.ImageDownloadManager;
import cn.ml.saddhu.bihudaily.engine.util.FileUtils;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

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
    @ViewById
    PhotoDraweeView photo_viewer;
    @Extra
    String extra_image_url;

    private boolean isStatusHiding = true;
    private boolean hasDownload;

    @AfterViews
    void afterView() {
        if (ImageDownloadManager.getInstance().checkInTask(extra_image_url)) {
            ImageDownloadManager.getInstance().registerGlobalListener(extra_image_url, this);
            progressBar.setVisibility(View.VISIBLE);
            photo_viewer.setVisibility(View.GONE);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.loadingbarColor), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            hasDownload = true;
            progressBar.setVisibility(View.GONE);
            photo_viewer.setVisibility(View.VISIBLE);
            File imageCacheFile = FileUtils.getImageCacheFile(extra_image_url);
            if (imageCacheFile.exists()) {
                photo_viewer.setPhotoUri(Uri.fromFile(imageCacheFile));
            } else {
                photo_viewer.setPhotoUri(Uri.parse(extra_image_url));
            }
        }
        photo_viewer.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (isStatusHiding) {
                    showStatusBar();
                } else {
                    hideStatusBar();
                }
            }
        });
        setSupportActionBar(toolbar_imageviewer);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                            Logger.i("The system bars are visible. Make any desired");
                            isStatusHiding = false;
                            getSupportActionBar().show();
                        } else {
                            //  The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                            Logger.i("The system bars are NOT visible. Make any desired");
                            isStatusHiding = true;
                            getSupportActionBar().hide();
                        }
                    }
                });
        hideStatusBar();
    }

    private void showStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.act_save_image) {
            savePicture();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存图片
     */
    private void savePicture() {
        if (hasDownload) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                String absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                File destFile = new File(absolutePath, getString(R.string.app_name));
                if (!destFile.exists() || !destFile.isDirectory()) {
                    destFile.mkdirs();
                }
                File imageCacheFile = FileUtils.getImageCacheFile(extra_image_url);
                destFile = new File(destFile, FileUtils.getImageSaveName(extra_image_url));
                FileUtils.copy(imageCacheFile, destFile);
                // 通知重新扫描
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destFile));
                sendBroadcast(mediaScanIntent);
                showToast(getString(R.string.save_success));
                Logger.i(absolutePath);
            } else {
                showToast(getString(R.string.please_insert_sdcart));
            }
        } else {
            showToast(getString(R.string.is_loading));
        }

    }


    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(String localPath, String orginPath) {
        Logger.i("localpath:" + localPath);
        try {
            hasDownload = true;
            progressBar.setVisibility(View.GONE);
            photo_viewer.setVisibility(View.VISIBLE);
            photo_viewer.setPhotoUri(Uri.fromFile(new File(localPath)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStatusHiding) {
            hideStatusBar();
        } else {
            showStatusBar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageDownloadManager.getInstance().unRegisterGlobalListener(extra_image_url);
    }
}
