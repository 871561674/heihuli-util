package com.heihuli.test;

import com.heihuli.util.CommonDateUtil;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author heihuli
 */
public class CommonDateUtilTest {

    /**
     * CommonDateUtil.addTimesStr
     */
    @Test
    public void test() {
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 1, ""));
        // 2019-12-31 17:40:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 2, ""));
        // 2022-09-30 17:40:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 5, ""));
        // 2022-12-28 17:40:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 7, ""));
        // 2022-12-28 17:40:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 10, ""));
        // 2022-12-31 14:40:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 11, ""));
        // 2022-12-31 14:40:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 12, ""));
        // 2022-12-31 17:37:22
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 13, ""));
        // 2022-12-31 17:40:19
        System.out.println(CommonDateUtil.addTimeStr("2022-12-31 17:40:22", -3, 14, ""));
        // 2022-12-31 17:40:21
    }

    /**
     * CommonDateUtil.addTime
     * CommonDateUtil.diffDate
     */
    @Test
    public void test1() {
        // 测试5天 每个维度差值
        Date now = new Date();
        Date date = CommonDateUtil.addTime(now, -5, 5);
        System.out.println(date);
        // Sun Nov 13 03:43:05 CST 2022
        System.out.println(CommonDateUtil.diffDate(now, date, 5));
        // 日 5
        System.out.println(CommonDateUtil.diffDate(now, date, 11));
        // 时 120
        System.out.println(CommonDateUtil.diffDate(now, date, 12));
        // 分 7200
        System.out.println(CommonDateUtil.diffDate(now, date, 13));
        // 秒 432000
        System.out.println(CommonDateUtil.diffDate(now, date, 14));
        //  毫秒 432000000
    }

    /**
     * CommonDateUtil.diffDateStr
     */
    @Test
    public void test2() {
        // 测试5天 每个维度差值
        String late = "2022-11-18 23:24:24";
        String early = "2022-11-13 23:24:24";
        System.out.println(CommonDateUtil.diffDateStr(late, early, 5, null));
        // 日 5
        System.out.println(CommonDateUtil.diffDateStr(late, early, 11, null));
        // 时 120
        System.out.println(CommonDateUtil.diffDateStr(late, early, 12, null));
        // 分 7200
        System.out.println(CommonDateUtil.diffDateStr(late, early, 13, null));
        // 秒 432000
        System.out.println(CommonDateUtil.diffDateStr(late, early, 14, null));
        // 毫秒 432000000
        System.out.println(CommonDateUtil.diffDateStr(late, early, null, null));
        // 默认毫秒 432000000
    }

    /**
     * CommonDateUtil.getFirstTimeOfDate
     * CommonDateUtil.getLastTimeOfDate
     * CommonDateUtil.formatDateByFormat
     */
    @Test
    public void test3() {
        Date first = CommonDateUtil.getFirstTimeOfDate(new Date());
        System.out.println(first);
        // Fri Nov 18 00:00:00 CST 2022
        System.out.println(CommonDateUtil.formatDateByFormat(first, null));
        // 2022-11-18 00:00:00

        Date last = CommonDateUtil.getLastTimeOfDate(new Date());
        System.out.println(last);
        // Fri Nov 18 23:59:59 CST 2022
        System.out.println(CommonDateUtil.formatDateByFormat(last, null));
        // 2022-11-18 23:59:59
    }

    /**
     * CommonDateUtil.parseSqlDateStr
     * CommonDateUtil.parseSqlDate
     * CommonDateUtil.parseTimestampStr
     * CommonDateUtil.parseTimestamp
     */
    @Test
    public void test4() {
        // 转sql Date

        String timeMill = "2022-11-18 23:24:24222";
        Date dateMill = new Date();
        System.out.println(CommonDateUtil.parseSqlDateStr(timeMill, "yyyy-MM-dd HH:mm:ssSSS"));
        // 2022-11-18
        System.out.println(CommonDateUtil.parseSqlDate(dateMill));
        // 2022-11-22

        Timestamp timestampMill = CommonDateUtil.parseTimestampStr(timeMill, "yyyy-MM-dd HH:mm:ssSSS");
        System.out.println(timestampMill);
        // 2022-11-18 23:24:24.222

        System.out.println(CommonDateUtil.parseTimestamp(dateMill));
        // 2022-11-22 04:18:00.269

        String time = "2022-11-18 23:24:24";
        Date date = new Date();
        System.out.println(CommonDateUtil.parseSqlDateStr(time, null));
        // 2022-11-18
        System.out.println(CommonDateUtil.parseSqlDate(date));
        // 2022-11-22

        Timestamp timestamp = CommonDateUtil.parseTimestampStr(time, null);
        System.out.println(timestamp);
        // 2022-11-18 23:24:24.0

        System.out.println(CommonDateUtil.parseTimestamp(date));
        // 2022-11-22 04:18:00.287

    }

    /**
     * CommonDateUtil.getYear
     * CommonDateUtil.getMonth
     * CommonDateUtil.getDay
     * CommonDateUtil.getHour
     * CommonDateUtil.getMinute
     * CommonDateUtil.getSecond
     * CommonDateUtil.getMillis
     */
    @Test
    public void test5() {
        Date date = new Date();
        System.out.println(CommonDateUtil.getYear(date));
        // 2022
        System.out.println(CommonDateUtil.getMonth(date));
        // 11
        System.out.println(CommonDateUtil.getDay(date));
        // 18
        System.out.println(CommonDateUtil.getHour(date));
        // 4
        System.out.println(CommonDateUtil.getMinute(date));
        // 43
        System.out.println(CommonDateUtil.getSecond(date));
        // 34
        System.out.println(CommonDateUtil.getMillis(date));
        // 475
    }

    /**
     * CommonDateUtil.getDateStringFromTimestamp
     * CommonDateUtil.getTimestampFromDateString
     */
    @Test
    public void test6() {
        long now = System.currentTimeMillis();
        System.out.println(CommonDateUtil.getDateStringFromTimestamp(now, ""));
        // 2022-11-22 04:54:58
        System.out.println(CommonDateUtil.getTimestampFromDateString("2022-11-22 04:54:22", ""));
        // 1669064062000
    }

    @Test
    public void test7() {
        System.out.println(CommonDateUtil.isTodayLater(System.currentTimeMillis()));
        // true
        System.out.println(CommonDateUtil.isTodayLater(System.currentTimeMillis() - 1000 * 60 * 60 *24));
        // false


    }
}
