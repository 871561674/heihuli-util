package com.heihuli.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Cron表达式工具类
 *
 * @author heihuli
 */
public class CronUtil {

    private static final Logger logger = LoggerFactory.getLogger(CronUtil.class);

    /**
     * 默认时区 Asia/Shanghai
     */
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");

    /**
     * 根据 cron 表达式解析时间间隔, 单位: 毫秒<br>
     * 如果表达式不合法, 返回 -1
     *
     * @param cron 表达式
     * @return 表达式生成的时间的间隔区间 单位: 毫秒
     */
    public static long getPeriod(String cron) {
        if (!CronSequenceGenerator.isValidExpression(cron)) {
            logger.error("Cron Util, invalid cron express: {}", cron);
            return -1;
        }
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron, TimeZone.getTimeZone(DEFAULT_ZONE_ID));
        Date currentTime = new Date();
        Date nextTimePoint = cronSequenceGenerator.next(currentTime);
        Date nnTimePoint = cronSequenceGenerator.next(nextTimePoint);
        return nnTimePoint.getTime() - nextTimePoint.getTime();
    }

}
