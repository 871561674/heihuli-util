package com.heihuli.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文工具类
 *
 * @author heihuli
 */
@Component
public class SpringContextAwareUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextAwareUtil.applicationContext = applicationContext;
    }

    /**
     * 获取上下文
     *
     * @return Spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取Spring配置
     *
     * @param key 配置名称
     * @return 配置值
     */
    public static String getProperties(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取Spring配置<br>
     * 没有配置时，返回默认值
     *
     * @param key          配置名称
     * @param defaultValue 默认值
     * @return 配置值
     */
    public static String getProperties(String key, String defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, defaultValue);
    }
}
