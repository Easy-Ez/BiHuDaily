package cn.ml.saddhu.bihudaily.mvp.model.impl;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.ml.saddhu.bihudaily.DBHelper;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDao;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.TopStory;
import cn.ml.saddhu.bihudaily.engine.domain.TopStoryDao;
import cn.ml.saddhu.bihudaily.mvp.api.APIHelper;
import cn.ml.saddhu.bihudaily.mvp.api.APIService;
import cn.ml.saddhu.bihudaily.mvp.model.StoryListModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 新闻列表Model类
 */
public class StoryListModelImpl implements StoryListModel {


    private final TopStoryDao mTopStoryDao;
    private final StoryDao mStoryDao;
    private final APIService apiService;
    private final SimpleDateFormat mParse;
    private final SimpleDateFormat mFormat;

    public StoryListModelImpl() {
        mTopStoryDao = DBHelper.getInstance().getDaoSession().getTopStoryDao();
        mStoryDao = DBHelper.getInstance().getDaoSession().getStoryDao();
        apiService = APIHelper.getInstance().create(APIService.class);

        mFormat = new SimpleDateFormat("MM月dd日 E", Locale.CHINA);
        mParse = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

    }

    @Override
    public void getHomePageList(final OnRefreshListener onRefreshListener) {
        Call<StoryInfo> homePageListCall = apiService.getHomePageList();
        homePageListCall.enqueue(new Callback<StoryInfo>() {
            @Override
            public void onResponse(Call<StoryInfo> call, Response<StoryInfo> response) {
                StoryInfo body = response.body();
                if (body != null) {
                    // step0处理数据
                    body.stories.get(0).isTag = true;
                    for (int i = 0; i < body.stories.size(); i++) {
                        body.stories.get(i).tagName = "今日热闻";
                        body.stories.get(i).date = body.date;
                    }
                    // step1显示数据
                    onRefreshListener.onSuccuss(body);
                    // step2先删除
                    mTopStoryDao.deleteAll();
                    // 如果今天已经获取过,只替换今日的,否则删除所有
                    List<Story> oldList = mStoryDao.queryBuilder()
                            .where(StoryDao.Properties.TagName.eq("今日热闻"))
                            .list();
                    if (oldList != null && oldList.size() > 0 && oldList.get(0).date.equals(body.date)) {
                        // 删除旧的今日热闻
                        mStoryDao.queryBuilder()
                                .where(StoryDao.Properties.Date.eq(body.date))
                                .buildDelete()
                                .executeDeleteWithoutDetachingEntities();
                    } else {
                        mStoryDao.deleteAll();
                    }
                    // step3存起来
                    mStoryDao.insertOrReplaceInTx(body.stories);
                    mTopStoryDao.insertOrReplaceInTx(body.topStories);
                } else {
                    onRefreshListener.onError(1);
                }
            }

            @Override
            public void onFailure(Call<StoryInfo> call, Throwable t) {
                // step1 先查数据库
                List<Story> stories = mStoryDao.queryBuilder().where(StoryDao.Properties.TagName.eq("今日热闻")).build().list();
                if (stories != null && stories.size() > 0) {
                    List<TopStory> topStories = mTopStoryDao.queryBuilder().list();
                    StoryInfo info = new StoryInfo();
                    info.date = stories.get(0).date;
                    info.topStories = topStories;
                    info.stories = stories;
                    onRefreshListener.onSuccuss(info);
                } else {
                    onRefreshListener.onError(2);
                }


            }
        });

    }

    @Override
    public void loadMoreHomePageList(final OnLoadMoreListener onLoadMoreListener, final String date) {
        Call<StoryInfo> storyInfoCall = apiService.loadMoreHomePageList(date);
        storyInfoCall.enqueue(new Callback<StoryInfo>() {
            @Override
            public void onResponse(Call<StoryInfo> call, Response<StoryInfo> response) {

                if (response.body() != null) {
                    // step0 处理数据
                    String formatDate = null;
                    try {
                        Date date = mParse.parse(response.body().date);
                        formatDate = mFormat.format(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    response.body().stories.get(0).isTag = true;
                    for (int i = 0; i < response.body().stories.size(); i++) {
                        response.body().stories.get(i).date = response.body().date;
                        if (!TextUtils.isEmpty(formatDate)) {
                            response.body().stories.get(i).tagName = formatDate;
                        }
                    }
                    // step1 存起来
                    mStoryDao.insertOrReplaceInTx(response.body().stories);
                    // step2 回调
                    onLoadMoreListener.onSuccuss(response.body().stories);

                }
            }

            @Override
            public void onFailure(Call<StoryInfo> call, Throwable t) {
                try {
                    Date time = mParse.parse(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(time);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    String format = mParse.format(calendar.getTime());
                    List<Story> list = mStoryDao
                            .queryBuilder()
                            .where(StoryDao.Properties.Date.eq(format))
                            .orderAsc(StoryDao.Properties.Date)
                            .list();
                    if (list != null && list.size() > 0) {
                        onLoadMoreListener.onSuccuss(list);
                    } else {
                        onLoadMoreListener.onError(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    onLoadMoreListener.onError(1);
                }


            }
        });

    }
}
