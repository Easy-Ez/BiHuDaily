package cn.ml.saddhu.bihudaily.mvp.model.impl;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.ml.saddhu.bihudaily.engine.api.APIHelper;
import cn.ml.saddhu.bihudaily.engine.api.APIService;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.db.DBHelper;
import cn.ml.saddhu.bihudaily.engine.domain.ReadHistory;
import cn.ml.saddhu.bihudaily.engine.domain.ReadHistoryDao;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDao;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.TopStory;
import cn.ml.saddhu.bihudaily.engine.domain.TopStoryDao;
import cn.ml.saddhu.bihudaily.mvp.model.StoryListModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 新闻列表Model类
 */
public class StoryListModelImpl extends BaseModelImpl<StoryInfo, List<Story>> implements StoryListModel {


    private final TopStoryDao mTopStoryDao;
    private final StoryDao mStoryDao;
    private final APIService apiService;
    private final SimpleDateFormat mParse;
    private final SimpleDateFormat mFormat;
    private Call<StoryInfo> homePageListCall;
    private Call<StoryInfo> storyInfoCall;
    private final ReadHistoryDao mReadHistoryDao;

    public StoryListModelImpl(OnNetRefreshListener<StoryInfo> refreshListener, OnNetLoadMoreListener<List<Story>> loadMoreListener) {
        super(refreshListener, loadMoreListener);
        mTopStoryDao = DBHelper.getInstance().getDaoSession().getTopStoryDao();
        mStoryDao = DBHelper.getInstance().getDaoSession().getStoryDao();
        mReadHistoryDao = DBHelper.getInstance().getDaoSession().getReadHistoryDao();
        apiService = APIHelper.getInstance().create(APIService.class);

        mFormat = new SimpleDateFormat("MM月dd日 E", Locale.CHINA);
        mParse = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    }

    @Override
    public void getHomePageList() {
        homePageListCall = apiService.getHomePageList();
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
                    mRefreshListener.onRefreshSuccess(body);
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
                    // 是否已读
                    for (int i = 0; i < body.stories.size(); i++) {
                        if (mReadHistoryDao
                                .queryBuilder()
                                .where(ReadHistoryDao.Properties.StoryId.eq(body.stories.get(i).getId()))
                                .unique() != null) {
                            body.stories.get(i).setIsRead(true);
                        }
                    }
                    // step3存起来
                    mStoryDao.insertOrReplaceInTx(body.stories);
                    mTopStoryDao.insertOrReplaceInTx(body.topStories);
                } else {
                    mRefreshListener.onRefreshError(1);
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
                    mRefreshListener.onRefreshSuccess(info);
                } else {
                    mRefreshListener.onRefreshError(2);
                }


            }
        });

    }

    @Override
    public void loadMoreHomePageList(final String date) {
        storyInfoCall = apiService.loadMoreHomePageList(date);
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
                        // 是否已读
                        if (mReadHistoryDao
                                .queryBuilder()
                                .where(ReadHistoryDao.Properties.StoryId.eq(response.body().stories.get(i).getId()))
                                .unique() != null) {
                            response.body().stories.get(i).setIsRead(true);
                        }
                        if (!TextUtils.isEmpty(formatDate)) {
                            response.body().stories.get(i).tagName = formatDate;
                        }

                    }
                    // step1 存起来
                    mStoryDao.insertOrReplaceInTx(response.body().stories);
                    // step2 回调
                    mLoadMoreListener.onLoadMoreSuccess(response.body().stories);

                }
            }

            @Override
            public void onFailure(Call<StoryInfo> call, Throwable t) {

                if (call.isCanceled()) {
                } else {
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
                            mLoadMoreListener.onLoadMoreSuccess(list);
                        } else {
                            mLoadMoreListener.onLoadMoreError(0);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        mLoadMoreListener.onLoadMoreError(1);
                    }

                }


            }
        });
    }

    @Override
    public void setItemRead(final String id) {
        DBHelper.getInstance().getDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                ReadHistory readHistory = new ReadHistory();
                readHistory.setStoryId(id);
                mReadHistoryDao.insertOrReplace(readHistory);
            }
        });

    }

    @Override
    public void onDestroy() {
        if (homePageListCall != null) homePageListCall.cancel();
        if (storyInfoCall != null) storyInfoCall.cancel();
    }
}
