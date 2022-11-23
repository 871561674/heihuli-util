package com.heihuli.test;

import com.heihuli.domain.ListObj;
import com.heihuli.util.CommonCheckUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heihuli
 */
public class CommonCheckUtilTest {

    /**
     * CommonCheckUtil.hasNullIn
     */
    @Test
    public void test() {
        List list = null;
        String a = null;
        Integer b = null;
        System.out.println(CommonCheckUtil.hasNullIn(list, a, b));
        // true
    }

    /**
     * CommonCheckUtil.IsInArray
     */
    @Test
    public void test2() {
        String[] arr = {"heihuli","aaa"};
        String a = "heihuli";
        System.out.println(CommonCheckUtil.IsInArray(a, arr));
        // true
    }

    /**
     * CommonCheckUtil.existConst
     */
    @Test
    public void test3() {
        System.out.println(CommonCheckUtil.existConst("red", ListObj.class));
        // true
        System.out.println(CommonCheckUtil.existConst("black", ListObj.Type.class));
        // true
    }
}
