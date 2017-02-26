package cn.ml.saddhu.bihudaily.engine.domain;

import com.orhanobut.logger.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by sadhu on 2017/2/25.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryDetailTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void sectionOrThemeInfo() throws Exception {
        StoryDetail storyDetail = new StoryDetail();
        storyDetail.section = new Section();
        storyDetail.section.setId(11);
        storyDetail.section.setName("section_name");
        storyDetail.section.setThumbnailUrl("www.baidu.com");
        String result = storyDetail.sectionOrThemeInfo();
        Logger.d(result);
    }

}