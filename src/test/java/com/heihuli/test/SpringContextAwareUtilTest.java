package com.heihuli.test;

import com.heihuli.util.SpringContextAwareUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * @author heihuli
 */
public class SpringContextAwareUtilTest {

    @Test
    public void test() {
        ApplicationContext context = SpringContextAwareUtil.getApplicationContext();
        System.out.println(context);
    }
}
