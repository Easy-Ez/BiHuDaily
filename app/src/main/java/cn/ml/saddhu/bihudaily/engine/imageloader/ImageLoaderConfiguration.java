package cn.ml.saddhu.bihudaily.engine.imageloader;

import android.content.Context;

/**
 * Created by sadhu on 2017/2/20.
 * Email static.sadhu@gmail.com
 */
public final class ImageLoaderConfiguration {

    final Context context;

    final int mConnectTimeOut;
    final int mReadTimeOut;
    final String mDiskCacheDirName;
    final int mMaxMemoryCache;
    final int mMaxDiskCache;

    private ImageLoaderConfiguration(final Builder builder) {
        context = builder.context;
        mConnectTimeOut = builder.mConnectTimeOut;
        mReadTimeOut = builder.mReadTimeOut;
        mDiskCacheDirName = builder.mDiskCacheDirName;
        mMaxMemoryCache = builder.mMaxMemoryCache;
        mMaxDiskCache = builder.mMaxDiskCache;
    }

    public static ImageLoaderConfiguration createDefault(Context context) {
        return new ImageLoaderConfiguration.Builder(context).build();
    }

    public static class Builder {
        private Context context;

        private int mConnectTimeOut = 10 * 1000;
        private int mReadTimeOut = 10 * 1000;
        private String mDiskCacheDirName = "image";
        private int mMaxMemoryCache = 10 * 1024 * 1024;
        private int mMaxDiskCache = 20 * 1024 * 1024;


        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Builder setConnectTimeOut(int mConnectTimeOut) {
            this.mConnectTimeOut = mConnectTimeOut;
            return this;
        }

        public Builder setReadTimeOut(int mReadTimeOut) {
            this.mReadTimeOut = mReadTimeOut;
            return this;
        }

        public Builder setDiskCacheDirName(String mDiskCacheDirName) {
            this.mDiskCacheDirName = mDiskCacheDirName;
            return this;
        }

        public Builder setMaxMemoryCache(int mMaxMemoryCache) {
            this.mMaxMemoryCache = mMaxMemoryCache;
            return this;
        }

        public Builder setMaxDiskCache(int mMaxDiskCache) {
            this.mMaxDiskCache = mMaxDiskCache;
            return this;
        }


        public ImageLoaderConfiguration build() {
            return new ImageLoaderConfiguration(this);
        }

    }
}
