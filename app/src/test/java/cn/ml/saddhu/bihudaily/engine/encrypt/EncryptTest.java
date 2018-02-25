package cn.ml.saddhu.bihudaily.engine.encrypt;

import org.json.JSONObject;
import org.junit.Test;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.domain.TokenBean;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2017/3/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class EncryptTest {
    @Test
    public void generateAnonymousData() throws Exception {
        JSONObject jsonObject = new JSONObject();
        String data = encrypt.generateAnonymousData();
        jsonObject.put("data", data);
        APIService apiService = APIHelper.getInstance().create(APIService.class);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<TokenBean> responseBodyCall = apiService.anonymousLogin(requestBody);
        responseBodyCall.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}