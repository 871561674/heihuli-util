package com.heihuli.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heihuli
 *
 * 线程工厂工具类
 * 用于添加线程名前缀
 */
public class CommonThreadFactoryUtil implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;


    private CommonThreadFactoryUtil(String prefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = prefix + "-thread-";
    }

    /**
     * 获取线程工厂
     *
     * @param prefix 前缀
     * @return
     */
    public static ThreadFactory getFactory(String prefix) {
        return new CommonThreadFactoryUtil(prefix);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

}
