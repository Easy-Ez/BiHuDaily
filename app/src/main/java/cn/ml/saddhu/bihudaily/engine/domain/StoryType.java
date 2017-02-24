package cn.ml.saddhu.bihudaily.engine.domain;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */

public enum StoryType {
    NORMAL("NORMAL", 0), WEB("WEB", 1), TOPIC("TOPIC", 2), CARD("CARD", 3);
    private String name;
    private int type;

    StoryType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public int value() {
        return this.type;
    }
}
