package com.heihuli.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author heihuli
 */
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 批处理设置超时key
     *
     * @param redisTemplate
     * @param key
     * @param value
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return
     */
    public static boolean setIfAbsentWithExpire(RedisTemplate<String, Object> redisTemplate,
                                                String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
                @Override
                public Boolean execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    redisOperations.opsForValue().setIfAbsent(key, value);
                    redisOperations.expire(key, timeout, timeUnit);
                    List<Object> result = redisOperations.exec();
                    if ((Boolean) result.get(0)) {
                        return true;
                    } else {
                        logger.info("[RedisUtil] key {} exists, exit", key);
                        return false;
                    }
                }
            };

            return redisTemplate.execute(sessionCallback);
        } catch (Exception e) {
            logger.error("[RedisUtil] get redisKey: {}, error: {}", key, e.getMessage());
            return false;
        }
    }
}
