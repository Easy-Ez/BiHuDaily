package cn.ml.saddhu.bihudaily.engine.push;

/**
 * Created by sadhu on 2017/6/13.
 */
public interface IPushEngine {
    void registerPush();

    void unRegisterPush();

    void setTag();
}
