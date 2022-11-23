package com.heihuli.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * 通用的 for 循环并行请求。适用于
 * 1. 每个 for 循环可独立执行，返回 List<R>，然后需要将所有任务的结果汇总返回
 * 2. 因为是并行执行，返回结果元素顺序不定，如果有顺序需求，需要再单独排序
 * 3. 如果一个子任务报错了，只会打印一行错误日志，不影响其他任务的执行
 * 4. 需要等待所有任务都退出后才返回
 *
 * @author heihuli
 */
public class ParallelUtil {

    private static Logger logger = LoggerFactory.getLogger(ParallelUtil.class);

    // 线程池是为了复用，应该定义成全局变量，也不需要shutdown
    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    /**
     * @param function 可并行 for 循环的查询任务，如并行查询数据库
     * @param paramList 任务入参列表，Collection 类型，每个线程取一个元素执行
     * @param logKey 日志关键字，日志输出时用，方便标识任务信息
     * @return
     * @param <T> 任务入参类型
     * @param <R> 结果元素类型
     * @throws InterruptedException
     */
    public static <T, R> List<R> parallelTask(Function<T, List<R>> function, Collection<T> paramList, String logKey) {
        long start = System.currentTimeMillis();
        // 需要 thread safe 的 list
        List<R> result = Collections.synchronizedList(new ArrayList<>());
        try {
            CountDownLatch countDownLatch = new CountDownLatch(paramList.size());
            for (T param : paramList) {
                executorService.submit(() -> {
                    try {
                        List<R> tempResult = function.apply(param);
                        result.addAll(tempResult);
                    } catch (Exception e) {
                        logger.error("[parallelTask] log key:{}, param:{}", logKey, param, e);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            long cost = System.currentTimeMillis() - start;
            // 超过 15s，默认都打印耗时，其他的只打印 debug
            if (cost > 15 * 1000) {
                logger.info("[parallelTask] log key:{}, params:{}, cost:{}", logKey, paramList, cost);
            } else if (logger.isDebugEnabled()) {
                logger.debug("[parallelTask] log key:{}, params:{}, cost:{}", logKey, paramList, cost);
            }
        } catch (Exception e) {
            logger.error("[parallelTask] log key:{}, params:{}", logKey, paramList, e);
        }
        return result;
    }
}
