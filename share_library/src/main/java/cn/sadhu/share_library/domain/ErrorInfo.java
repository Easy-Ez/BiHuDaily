package cn.sadhu.share_library.domain;

/**
 * Created by sadhu on 2017/5/12.
 */

public class ErrorInfo {
    public int code;
    public String message;

    public ErrorInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
