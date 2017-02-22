package cn.ml.saddhu.bihudaily.engine.util;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StringUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void integer2StringWithThousand() throws Exception {
        Assert.assertEquals("integer2StringWithThousand", StringUtils.integer2StringWithThousand(10100),"10.1K");
    }

}