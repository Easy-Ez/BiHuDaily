package cn.sadhu.share_library.domain;

/**
 * Created by sadhu on 2017/5/12.
 */
public class ShareInfoBean {
    private String mTittle;
    private String mSummary;
    private String mTargetUrl;
    private String mImageUrl;

    public ShareInfoBean(String tittle, String summary, String targetUrl, String imageUrl) {
        mTittle = tittle;
        mSummary = summary;
        mTargetUrl = targetUrl;
        mImageUrl = imageUrl;
    }

    public String getTittle() {
        return mTittle;
    }

    public void setTittle(String tittle) {
        mTittle = tittle;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTargetUrl() {
        return mTargetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        mTargetUrl = targetUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
