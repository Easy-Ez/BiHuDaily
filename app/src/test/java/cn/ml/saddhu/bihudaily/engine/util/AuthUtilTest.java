package cn.ml.saddhu.bihudaily.engine.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sadhu on 2017/9/15.
 * 描述
 */
public class AuthUtilTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a() throws Exception {
        String a = AuthUtil.getAnonymousData();
        System.out.println(a);
    }

}