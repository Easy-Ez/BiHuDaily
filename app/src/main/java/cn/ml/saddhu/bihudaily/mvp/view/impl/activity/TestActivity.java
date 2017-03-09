package cn.ml.saddhu.bihudaily.mvp.view.impl.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import cn.ml.saddhu.bihudaily.R;

/**
 * Created by sadhu on 2017/2/27.
 * Email static.sadhu@gmail.com
 */
@EActivity(R.layout.act_test_popupwindow)
public class TestActivity extends AppCompatActivity {


    private PopupWindow popupWindow;

    @AfterViews
    public void afterViews() {
        initPopupWindow();
    }

    public void onBtn1Clicked(View view) {
        int[] LocationOnScreen = new int[2];
        view.getLocationOnScreen(LocationOnScreen);
        if (popupWindow.isShowing()) {
            if (isNearView(view)) {
                popupWindow.dismiss();
            } else {
                popupWindow.dismiss();
                popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, LocationOnScreen[0] - 360, LocationOnScreen[1]);
            }
        } else {
            popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, LocationOnScreen[0] - 360, LocationOnScreen[1]);
        }
    }

    public void onBtn2Cliked(View view) {
        int[] LocationOnScreen = new int[2];
        view.getLocationOnScreen(LocationOnScreen);
        if (popupWindow.isShowing()) {
            if (isNearView(view)) {
                popupWindow.dismiss();
            } else {
                popupWindow.dismiss();
                popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, LocationOnScreen[0] - 360, LocationOnScreen[1]);
            }
        } else {
            popupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, LocationOnScreen[0] - 360, LocationOnScreen[1]);
        }
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.popup_test, null);
        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private boolean isNearView(View v) {
        ViewGroup contentView = (ViewGroup) popupWindow.getContentView();
        int[] popupOnScreen = new int[2];
        contentView.getChildAt(0).getLocationOnScreen(popupOnScreen);

        int[] viewOnScreen = new int[2];
        v.getLocationInWindow(viewOnScreen);

        return popupOnScreen[0] + 360 == viewOnScreen[0] && popupOnScreen[1] == viewOnScreen[1];
    }

    @Click(R.id.ll_container)
    void onContainerClicked() {
        if (popupWindow.isShowing())
            popupWindow.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
