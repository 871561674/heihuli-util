package com.heihuli.test;

import com.heihuli.util.CommonThreadFactoryUtil;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author heihuli
 */
public class CommonThreadFactoryUtilTest {

    /**
     * CommonThreadFactoryUtil.getFactory
     * @param args
     */
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0
                , TimeUnit.SECONDS, new LinkedBlockingQueue<>(), CommonThreadFactoryUtil.getFactory("heihuli"));

        for (int i = 0; i < 200; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(5, 5, 0
                , TimeUnit.SECONDS, new LinkedBlockingQueue<>(), CommonThreadFactoryUtil.getFactory("heihuli1"));

        for (int i = 0; i < 200; i++) {
            executor2.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}
