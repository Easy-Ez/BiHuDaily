package cn.ml.saddhu.bihudaily.engine.domain;

/**
 * Created by sadhu on 2017/4/10.
 * Email static.sadhu@gmail.com
 * Describe: 错误信息
 */
public class ErrorMsgBean {
    public int code;
    public String msg;

    public ErrorMsgBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorMsgBean getNetError() {
        return new ErrorMsgBean(1, "网络连接失败");
    }
}
