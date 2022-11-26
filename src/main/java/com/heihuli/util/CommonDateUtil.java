package com.heihuli.util;

import com.heihuli.base.CommonStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期工具类
 *
 * @author heihuli
 */
public class CommonDateUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonDateUtil.class);

    /**
     * 1分钟 --> 60秒
     */
    public static final int PERIOD_MINUTE_SEC = 60;
    /**
     * 1小时 --> 3600秒
     */
    public static final int PERIOD_HOUR_SEC = 3600;
    /**
     * 1天 --> 86400秒
     */
    public static final int PERIOD_DAY_SEC = 86400;

    /**
     * 默认日期转换格式 yyyy-MM-dd HH:mm:ss
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 在传入的日期的基础上,加上若干时间<br>
     * 时间类型有 年:1, 月:2, 日(月):5, 日(周):7, 小时(12制):10, 小时(24制):11, 分钟:12, 秒:13, 毫秒:14
     *
     * @param dateStr 日期字符串
     * @param period  增量
     * @param type    类型 年:1, 月:2, 日(月):5, 日(周):7, 小时(12制):10, 小时(24制):11, 分钟:12, 秒:13, 毫秒:14
     * @param format  默认格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String addTimeStr(String dateStr, int period, Integer type, String format) {
        try {
            if (type == null && type != 1 && type != 2 && type != 5 && type != 7
                    && type != 10 && type != 11 && type != 12 && type != 13 && type != 14) {
                type = 5;
            }
            SimpleDateFormat df;
            if (CommonStringUtil.isBlank(format)) {
                df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            } else {
                df = new SimpleDateFormat(format);
            }
            return df.format(addTime(df.parse(dateStr), period, type));
        } catch (Exception e) {
            logger.error("[CommonDateUtil] addTimeStr failed, errMes: {}", e);
            return "";
        }
    }

    /**
     * 在传入的日期的基础上,加上若干时间<br>
     * 时间类型有 年:1, 月:2, 日(月):5, 日(周):7, 小时(12制):10, 小时(24制):11, 分钟:12, 秒:13, 毫秒:14
     *
     * @param date
     * @param period 增量
     * @param type   类型 年:1, 月:2, 日(月):5, 日(周):7, 小时(12制):10, 小时(24制):11, 分钟:12, 秒:13, 毫秒:14
     * @return
     */
    public static Date addTime(Date date, int period, Integer type) {
        try {
            if (type == null && type != 1 && type != 2 && type != 5 && type != 7
                    && type != 10 && type != 11 && type != 12 && type != 13 && type != 14) {
                type = 5;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(type, period);
            return calendar.getTime();
        } catch (Exception e) {
            logger.error("[CommonDateUtil] addTime failed, errMes: {}", e);
            return date;
        }
    }

    /**
     * 日期相减 late - early<br>
     * 时间类型有 日:5, 时:11, 分钟:12, 秒:13, 毫秒:14
     *
     * @param late
     * @param early
     * @param type  时间类型有 日:5, 时:11, 分钟:12, 秒:13, 毫秒:14
     * @return 返回相减后的日期，默认返回毫秒
     */
    public static long diffDate(Date late, Date early, Integer type) {
        if (type == null) {
            logger.warn("[CommonDateUtil] diffDate type is null, return diff-millis");
            return late.getTime() - early.getTime();
        }
        switch (type) {
            case Calendar.DAY_OF_MONTH:
                return (late.getTime() - early.getTime()) / (24 * 60 * 60 * 1000);
            case Calendar.HOUR_OF_DAY:
                return (late.getTime() - early.getTime()) / (60 * 60 * 1000);
            case Calendar.MINUTE:
                return (late.getTime() - early.getTime()) / (60 * 1000);
            case Calendar.SECOND:
                return (late.getTime() - early.getTime()) / 1000;
            case Calendar.MILLISECOND:
                return late.getTime() - early.getTime();
            default:
                logger.warn("[CommonDateUtil] diffDate type is invalid, return diff-millis");
                return late.getTime() - early.getTime();
        }
    }

    /**
     * 日期相减 late - early<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss<br>
     * 时间类型有 日:5, 时:11, 分钟:12, 秒:13, 毫秒:14
     *
     * @param lateStr
     * @param earlyStr
     * @param format
     * @param type     时间类型有 日:5, 时:11, 分钟:12, 秒:13, 毫秒:14
     * @return 返回相减后的日期, 默认返回毫秒
     */
    public static long diffDateStr(String lateStr, String earlyStr, Integer type, String format) {
        if (type == null) {
            logger.warn("[CommonDateUtil] diffDateDayStr type is null, return diff-millis");
        }
        try {
            if (CommonStringUtil.isBlank(format)) {
                format = DEFAULT_DATE_FORMAT;
            }
            Date late = parseDate(lateStr, format);
            Date early = parseDate(earlyStr, format);
            return diffDate(late, early, type);
        } catch (Exception e) {
            logger.error("[CommonDateUtil] diffDateStr failed, errMsg: {}", e);
            return 0;
        }
    }

    /**
     * 获得一天中最早时刻
     *
     * @param date java.util.Date
     * @return java.util.Date
     */
    public static Date getFirstTimeOfDate(Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            String dateStr = dateFormat.format(date);
            dateStr = dateStr + " 00:00:00";
            return dateTimeFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("[CommonDateUtil] getFirstTimeOfDate failed, errMes: {}", e);
            return null;
        }
    }

    /**
     * 获得一天中最晚时刻
     *
     * @param date java.util.Date
     * @return java.util.Date
     */
    public static Date getLastTimeOfDate(Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            String dateStr = dateFormat.format(date);
            dateStr = dateStr + " 23:59:59";
            return dateTimeFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("[CommonDateUtil] getLastTimeOfDate failed, errMes: {}", e);
            return null;
        }
    }

    /**
     * 以指定的格式来格式化日期 java.util.Date --> String<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param format
     * @return String
     */
    public static String formatDateByFormat(Date date, String format) {
        try {
            if (CommonStringUtil.isBlank(format)) {
                format = DEFAULT_DATE_FORMAT;
            }
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            logger.error("[CommonDateUtil] formatDateByFormat failed, errMes: {}", e);
            return "";
        }
    }

    /**
     * 格式化日期 String --> java.util.Date<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @param format
     * @return 返回日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            if (CommonStringUtil.isBlank(format)) {
                format = DEFAULT_DATE_FORMAT;
            }
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (Exception e) {
            logger.error("[CommonDateUtil] parseDate failed, errMes: {}", e);
            return null;
        }
    }

    /**
     * String --> java.sql.Date<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss<br>
     * java.sql.Date只有年月日, 没有时分秒
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static java.sql.Date parseSqlDateStr(String dateStr, String format) {
        if (CommonStringUtil.isBlank(format)) {
            format = DEFAULT_DATE_FORMAT;
        }
        Date date = parseDate(dateStr, format);
        return parseSqlDate(date);
    }

    /**
     * java.util.Date --> java.sql.Date
     *
     * @param date
     * @return
     */
    public static java.sql.Date parseSqlDate(Date date) {
        try {
            return new java.sql.Date(date.getTime());
        } catch (Exception e) {
            logger.error("[CommonDateUtil] parseSqlDate failed, errMes: {}", e);
            return null;
        }
    }

    /**
     * String --> Timestamp<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Timestamp parseTimestampStr(String dateStr, String format) {
        if (CommonStringUtil.isBlank(format)) {
            format = DEFAULT_DATE_FORMAT;
        }
        Date date = parseDate(dateStr, format);
        if (date != null) {
            long t = date.getTime();
            return new Timestamp(t);
        } else
            return null;
    }

    /**
     * java.util.Date --> Timestamp<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Timestamp parseTimestamp(Date date) {
        if (date != null) {
            long t = date.getTime();
            return new Timestamp(t);
        } else
            return null;
    }

    /**
     *
     */
    /**
     * DateStr --> TimestampLong<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss<br>
     * 将字符串类型日期（格式：yyyy-MM-dd HH:mm:ss）转化为毫秒单位时间戳
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return
     */
    public static Long getTimestampFromDateString(String dateStr, String format) {
        if (CommonStringUtil.isNotBlank(dateStr)) {
            SimpleDateFormat simpleDateFormat;
            if (CommonStringUtil.isBlank(format)) {
                simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            } else {
                simpleDateFormat = new SimpleDateFormat(format);
            }
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(simpleDateFormat.parse(dateStr));
                return calendar.getTimeInMillis();
            } catch (ParseException pe) {
                logger.error("[CommonDateUtil] getTimestampFromDateString failed");
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    /**
     * TimestampLong --> DateStr<br>
     * 默认格式(format为空时) yyyy-MM-dd HH:mm:ss<br>
     * 将毫秒单位时间戳转化为字符串类型日期（格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param timestamp 毫秒时间戳
     * @param format    格式
     * @return
     */
    public static String getDateStringFromTimestamp(Long timestamp, String format) {
        if (timestamp == null) {
            logger.warn("[CommonDateUtil] getDateStringFromTimestamp request param: timestamp is null");
            return "";
        }
        Date date = new Date(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (CommonStringUtil.isBlank(format)) {
            simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        } else {
            simpleDateFormat = new SimpleDateFormat(format);
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 返回年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日份(DAY_OF_MONTH)
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MILLISECOND);
    }

}
