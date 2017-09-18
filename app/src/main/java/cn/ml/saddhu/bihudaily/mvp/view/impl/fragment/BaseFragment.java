package cn.ml.saddhu.bihudaily.mvp.view.impl.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import cn.ml.saddhu.bihudaily.mvp.view.IBaseView;

/**
 * Created by sadhu on 2017/9/18.
 * 描述
 */
public class BaseFragment extends Fragment implements IBaseView {
    @Override
    public final void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public final void showToast(String msg, int duration) {
        Toast.makeText(getContext(), msg, duration).show();
    }

    @Override
    public final void showToast(int res) {
        showToast(getString(res));
    }

}
