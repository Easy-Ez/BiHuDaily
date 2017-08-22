package cn.ml.saddhu.bihudaily.mvp.model.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.commondListener.NetCallback;
import cn.ml.saddhu.bihudaily.engine.domain.CommentBean;
import cn.ml.saddhu.bihudaily.engine.domain.ErrorMsgBean;
import cn.ml.saddhu.bihudaily.mvp.model.CommentsModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2017/4/9.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class CommentsModelImpl implements CommentsModel {


    private final APIService apiService;

    public CommentsModelImpl() {
        apiService = APIHelper.getInstance().create(APIService.class);
    }


    @Override
    public void getLongCommentsList(String storyId, final NetCallback<List<CommentBean>> callback) {
        Call<ResponseBody> call = apiService.getLongComments(storyId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                JSONArray comments = null;
                try {
                    JSONObject jsonObject = new JSONObject(body.string());
                    comments = jsonObject.getJSONArray("comments");
                    Gson gson = new Gson();
                    List<CommentBean> lists = gson.fromJson(comments.toString(), new TypeToken<List<CommentBean>>() {
                    }.getType());
                    callback.onSuccess(lists);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(ErrorMsgBean.getNetError());
            }
        });


    }

    @Override
    public void getMoreLongCommentsList(String storyId, String commentId, final NetCallback<List<CommentBean>> callback) {
        Call<ResponseBody> call = apiService.getMoreLongComments(storyId, commentId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                JSONArray comments = null;
                try {
                    JSONObject jsonObject = new JSONObject(body.string());
                    comments = jsonObject.getJSONArray("comments");
                    Gson gson = new Gson();
                    List<CommentBean> lists = gson.fromJson(comments.toString(), new TypeToken<List<CommentBean>>() {
                    }.getType());
                    callback.onSuccess(lists);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(ErrorMsgBean.getNetError());
            }
        });
    }

    @Override
    public void getShortCommentsList(String storyId, final NetCallback<List<CommentBean>> callback) {
        Call<ResponseBody> call = apiService.getShortComments(storyId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                JSONArray comments = null;
                try {
                    JSONObject jsonObject = new JSONObject(body.string());
                    comments = jsonObject.getJSONArray("comments");
                    Gson gson = new Gson();
                    List<CommentBean> lists = gson.fromJson(comments.toString(), new TypeToken<List<CommentBean>>() {
                    }.getType());
                    callback.onSuccess(lists);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(ErrorMsgBean.getNetError());
            }
        });
    }

    @Override
    public void getMoreShortCommentsList(String storyId, String commentId, final NetCallback<List<CommentBean>> callback) {
        Call<ResponseBody> call = apiService.getMoreShortComments(storyId, commentId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                JSONArray comments = null;
                try {
                    JSONObject jsonObject = new JSONObject(body.string());
                    comments = jsonObject.getJSONArray("comments");
                    Gson gson = new Gson();
                    List<CommentBean> lists = gson.fromJson(comments.toString(), new TypeToken<List<CommentBean>>() {
                    }.getType());
                    callback.onSuccess(lists);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(ErrorMsgBean.getNetError());
            }
        });
    }

    @Override
    public void onDestroy() {

    }

}
