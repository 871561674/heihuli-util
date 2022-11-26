package com.heihuli.test;

import com.heihuli.base.CommonStringUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author heihuli
 */
public class CommonStringUtilTest {

    /**
     * CommonStringUtil.isBlank
     * CommonStringUtil.isNotBlank
     */
    @Test
    public void test() {
        Assert.assertEquals(CommonStringUtil.isBlank(" "), true);
        Assert.assertEquals(CommonStringUtil.isBlank(""), true);
        Assert.assertEquals(CommonStringUtil.isBlank(null), true);
        Assert.assertEquals(CommonStringUtil.isBlank("null"), false);
        Assert.assertEquals(CommonStringUtil.isBlank("\n"), true);
        Assert.assertEquals(CommonStringUtil.isBlank("\r"), true);
        Assert.assertEquals(CommonStringUtil.isBlank("\t"), true);

        Assert.assertEquals(CommonStringUtil.isNotBlank(" "), false);
        Assert.assertEquals(CommonStringUtil.isNotBlank(""), false);
        Assert.assertEquals(CommonStringUtil.isNotBlank(null), false);
        Assert.assertEquals(CommonStringUtil.isNotBlank("null"), true);
        Assert.assertEquals(CommonStringUtil.isNotBlank("\n"), false);
        Assert.assertEquals(CommonStringUtil.isNotBlank("\r"), false);
        Assert.assertEquals(CommonStringUtil.isNotBlank("\t"), false);
    }

    /**
     * CommonStringUtil.uncapitalize
     */
    @Test
    public void test1() {
        System.out.println(CommonStringUtil.uncapitalize("SSAFASFASFddd"));
        // sSAFASFASFddd
        System.out.println(CommonStringUtil.uncapitalize("null"));
        // null
        System.out.println(CommonStringUtil.uncapitalize(null));
        // null
        System.out.println(CommonStringUtil.uncapitalize("aSAFAaFASFddd"));
        // aSAFAaFASFddd
    }

    /**
     * CommonStringUtil.random
     */
    @Test
    public void test2() {
        System.out.println(CommonStringUtil.random(10, "abcdefg"));
        // ecabbabbgd
    }
}
