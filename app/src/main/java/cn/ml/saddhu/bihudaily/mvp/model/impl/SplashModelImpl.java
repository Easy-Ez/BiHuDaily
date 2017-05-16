package cn.ml.saddhu.bihudaily.mvp.model.impl;

import com.orhanobut.logger.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.ml.saddhu.bihudaily.engine.db.DBHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.domain.Creative;
import cn.ml.saddhu.bihudaily.engine.domain.CreativeDao;
import cn.ml.saddhu.bihudaily.engine.domain.Creatives;
import cn.ml.saddhu.bihudaily.mvp.model.SplashModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2016/11/13.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class SplashModelImpl implements SplashModel {


    public static final String TAG = "SplashModelImpl";
    private final CreativeDao mCreativeDao;
    private Creative creative;
    private Call<Creatives> call;

    public SplashModelImpl() {
        mCreativeDao = DBHelper.getInstance().getDaoSession().getCreativeDao();
    }

    @Override
    public void getSplashInfo(int width, int height) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        long start = calendar.getTimeInMillis() / 1000;
        calendar.add(Calendar.DATE, 1);
        long end = calendar.getTimeInMillis() / 1000;
        List<Creative> list = mCreativeDao.queryBuilder().where(CreativeDao.Properties.StartTime.between(start, end)).build().list();
        if (list != null && list.size() > 0) {
            creative = list.get(0);
        } else {
            APIService apiService = APIHelper.getInstance().create(APIService.class);
            call = apiService.getSplashImage(String.format(Locale.CHINA, "%d*%d", width, height));

            call.enqueue(new Callback<Creatives>() {
                @Override
                public void onResponse(Call<Creatives> call, Response<Creatives> response) {
                    Creatives body = response.body();
                    if (body != null && body.creatives.size() > 0) {
                        creative = body.creatives.get(0);
                        mCreativeDao.deleteAll();
                        for (int i = 0; i < body.creatives.size(); i++) {
                            mCreativeDao.insertOrReplace(body.creatives.get(i));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Creatives> call, Throwable t) {
                    if (call.isCanceled()) {

                    } else {
                        Logger.e(t, "getSplashInfo");
                    }
                }
            });
        }


    }

    @Override
    public Creative getSplashBean() {
        return creative;
    }

    @Override
    public void onDestroy() {
        if (call != null) call.cancel();
    }
}
