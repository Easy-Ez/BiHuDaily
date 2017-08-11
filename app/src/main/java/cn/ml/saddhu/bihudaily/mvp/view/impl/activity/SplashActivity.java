package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.ml.saddhu.bihudaily.R;
import cn.ml.saddhu.bihudaily.engine.commondListener.SimpleAnimationListener;
import cn.ml.saddhu.bihudaily.engine.domain.Creative;
import cn.ml.saddhu.bihudaily.mvp.presenter.ISplashPresenter;
import cn.ml.saddhu.bihudaily.mvp.presenter.imp.SplashPresenterImpl;
import cn.ml.saddhu.bihudaily.mvp.view.ISplashView;

/**
 * Created by sadhu on 2016/11/7.
 * Email static.sadhu@gmail.com
 * Describe: 启动页
 */
@EActivity(R.layout.act_splash)
public class SplashActivity extends BaseActivity implements ISplashView {


    @ViewById(R.id.logo_container)
    View mLogoContainer;
    @ViewById(R.id.logo)
    ImageView mLogoImageView;
    @ViewById(R.id.splash_author)
    TextView mAuthorTextView;

    @ViewById(R.id.splash_img)
    SimpleDraweeView mSplashImageView;


    private ISplashPresenter mPresenter;
    private Animation.AnimationListener mLogoTransListener = new SimpleAnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mLogoImageView.setImageDrawable(AnimatedVectorDrawableCompat.create(SplashActivity.this, R.drawable.daily_logo));
            if ((mLogoImageView.getDrawable() instanceof Animatable)) {
                ((Animatable) mLogoImageView.getDrawable()).start();
                mPresenter.getCreative();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity_.intent(SplashActivity.this).start();
                        finish();
                    }
                }, 2000);
            }
        }
    };

    private Handler mHandler = new Handler();

    @AfterViews
    void afterViews() {
        mPresenter = new SplashPresenterImpl(this);
        mPresenter.fetchSpashInfo();
        Animation mLogoContainerAnim = AnimationUtils.loadAnimation(this, R.anim.anim_container_slide_in);
        mLogoContainerAnim.setAnimationListener(mLogoTransListener);
        mLogoContainer.startAnimation(mLogoContainerAnim);
    }

    @Override
    public void showImg(Creative creative) {
        if (creative != null) {
            Logger.d(creative.url);
            mSplashImageView.setImageURI(creative.url);
            mAuthorTextView.setText(creative.text);
        } else {
            mSplashImageView.setImageURI(ImageRequestBuilder.newBuilderWithResourceId(R.drawable.splash).getSourceUri());
        }
    }

    @Override
    public Point getSplashImageSize() {
        Point point = new Point();
        point.x = getResources().getDisplayMetrics().widthPixels;
        point.y = getResources().getDisplayMetrics().heightPixels - getResources().getDimensionPixelSize(R.dimen.activity_splash_bottom_height);
        return point;
    }


    private void hideNavigation() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 21) {
            decorView.setSystemUiVisibility(4098);
        } else {
            decorView.setSystemUiVisibility(2);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
