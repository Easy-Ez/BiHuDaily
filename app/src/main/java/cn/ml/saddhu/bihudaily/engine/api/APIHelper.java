package cn.ml.saddhu.bihudaily.engine.api;

import cn.ml.saddhu.bihudaily.engine.constant.HttpConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sadhu on 2016/11/8.
 * Email static.sadhu@gmail.com
 * Describe: 请求网络帮助类
 */
public class APIHelper {

    private static APIHelper mInstance;
    private final Retrofit retrofit;

    private APIHelper() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(HttpConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public static APIHelper getInstance() {
        if (mInstance == null) {
            synchronized (APIHelper.class) {
                if (mInstance == null) {
                    mInstance = new APIHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 创建一个请求
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
