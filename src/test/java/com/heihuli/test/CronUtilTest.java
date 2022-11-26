package com.heihuli.test;

import com.heihuli.util.CronUtil;
import org.junit.Test;

/**
 * @author heihuli
 */
public class CronUtilTest {

    /**
     * CronUtil.getPeriod
     */
    @Test
    public void test() {
        System.out.println(CronUtil.getPeriod("0 0 0 2 * ?"));
        // 2678400000
    }
}
