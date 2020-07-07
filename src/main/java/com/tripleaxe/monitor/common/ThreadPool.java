package com.tripleaxe.monitor.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Thread pool definition
 *
 * @author Tao
 */
public class ThreadPool {

    /**
     * 日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    /**
     * 通用线程池
     */
    private final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    /**
     * 默认线程超时时间
     */
    private final long timeOut;
    /**
     * 默认线程超时单位
     */
    private final TimeUnit timeUnit;

    /**
     * 线程名前缀
     */
    private final String threadNamePrefix;

    /**
     * 构造器
     *
     * @param timeOut
     * @param timeUnit
     * @param threadNamePrefix
     */
    public ThreadPool(long timeOut, TimeUnit timeUnit, String threadNamePrefix) {
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
        this.threadNamePrefix = threadNamePrefix;
        this.initialize();
        executor.initialize();
    }

    /**
     * 初始化线程;
     * 设置了coolPoolSize,maxPoolSize和ThreadNamePrefix
     * 如果需要修改其他线程池配置，子类可以通过重载此方法进行修改
     */
    protected void initialize() {
        executor.setCorePoolSize(CommonConsts.COMMON_POOL_SIZE);
        executor.setMaxPoolSize(CommonConsts.COMMON_POOL_SIZE);
        executor.setThreadNamePrefix(this.threadNamePrefix);
    }

    /**
     * 提交一个异步任务
     *
     * @param callable
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }

    /**
     * 提交一个runnable任务
     *
     * @param runnable
     * @return
     */
    public Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    /**
     * 添加多个并行任务并等待执行完毕
     *
     * @param callables
     */
    public void submitAndWait(Callable<?>... callables) {
        long start = 0;
        if (logger.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }
        List<Future<?>> futures = new ArrayList<>(callables.length);
        for (Callable<?> callable : callables) {
            futures.add(executor.submit(callable));
        }
        for (Future<?> future : futures) {
            try {
                future.get(timeOut, timeUnit);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error("ThreadPool#submitAndWait, 并行任务执行出错");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("ThreadPool#submitAndWait,  duration: {}ms", System.currentTimeMillis() - start);
        }
    }

    /**
     * 添加多个并行任务并等待执行完毕
     *
     * @param tasks
     */
    public void submitAndWait(Runnable... tasks) {
        long start = 0;
        if (logger.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }
        List<Future<?>> futures = new ArrayList<>(tasks.length);
        for (Runnable task : tasks) {
            futures.add(executor.submit(task));
        }
        for (Future<?> future : futures) {
            try {
                future.get(timeOut, timeUnit);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error("ThreadPool#submitAndWait, 并行任务执行出错", e);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("ThreadPool#submitAndWait, duration: {}ms", System.currentTimeMillis() - start);
        }
    }

}
