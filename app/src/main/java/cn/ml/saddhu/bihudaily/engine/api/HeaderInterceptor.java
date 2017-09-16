package cn.ml.saddhu.bihudaily.engine.api;

import android.os.Build;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.UUID;

import cn.ml.saddhu.bihudaily.MyApplication;
import cn.ml.saddhu.bihudaily.engine.domain.TokenBean;
import cn.ml.saddhu.bihudaily.engine.util.AuthUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by sadhu on 2017/9/16.
 * 描述
 */
public class HeaderInterceptor implements Interceptor {
    private String mUUID;
    private String mToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader("X-Api-Version", "4")
                .addHeader("X-App-Version", "2.6.0")
                .addHeader("X-OS", "Android " + Build.VERSION.RELEASE)
                .addHeader("X-Device", Build.MODEL);

        if (TextUtils.isEmpty(mUUID)) {
            getUUID();
        }
        if (!TextUtils.isEmpty(mUUID)) {
            builder.addHeader("X-UUID", mUUID);
        }
        if (!originalRequest.url().toString().equals("https://news-at.zhihu.com/api/4/anonymous-login"))
            if (TextUtils.isEmpty(mToken)) {
                getToken();
            }
        if (!TextUtils.isEmpty(mToken)) {
            builder.addHeader("Authorization", mToken);
        }


        builder.addHeader("ZA", "OS=Android " + Build.VERSION.RELEASE + "&Platform=" + Build.MODEL);

        StringBuffer stringBuffer = new StringBuffer("DailyApi/4");
        stringBuffer.append(" (");
        stringBuffer.append("Linux; Android ").append(Build.VERSION.RELEASE).append("; ");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append(" Build/").append(Build.BRAND).append("/").append(Build.PRODUCT).append("/").append(Build.DEVICE).append("/").append(Build.ID).append("/").append(Locale.getDefault().toString());
        stringBuffer.append(")");
        stringBuffer.append(" Google-HTTP-Java-Client/1.20.0 (gzip)");
        builder.addHeader("User-Agent", stringBuffer.toString());
        Request build = builder.build();
        return chain.proceed(build);
    }

    private void getUUID() {
        if (TextUtils.isEmpty(mUUID)) {
            File file = new File(MyApplication.mContext.getFilesDir(), "INSTALLATION");
            try {
                if (!file.exists()) {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    mUUID = UUID.randomUUID().toString();
                    fileOutputStream.write(mUUID.getBytes());
                    fileOutputStream.close();
                } else {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                    byte[] bArr = new byte[((int) randomAccessFile.length())];
                    randomAccessFile.readFully(bArr);
                    randomAccessFile.close();
                    mUUID = new String(bArr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getToken() {
        if (TextUtils.isEmpty(mToken)) {
            File file = new File(MyApplication.mContext.getFilesDir(), "TOKEN");
            try {
                if (!file.exists()) {
                    // 通过一个特定的接口获取token，此处要用到同步的retrofit请求
                    APIService service = APIHelper.getInstance().create(APIService.class);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", AuthUtil.getAnonymousData());
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                    Call<TokenBean> stringCall = service.anonymousLogin(body);
                    //要用retrofit的同步方式
                    TokenBean newToken = stringCall.execute().body();
                    if (newToken != null) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        mToken = "Bearer " + newToken.access_token;
                        fileOutputStream.write(mToken.getBytes());
                        fileOutputStream.close();
                        Logger.d("newToken:" + mToken);
                    }
                } else {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                    byte[] bArr = new byte[((int) randomAccessFile.length())];
                    randomAccessFile.readFully(bArr);
                    randomAccessFile.close();
                    mToken = new String(bArr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
