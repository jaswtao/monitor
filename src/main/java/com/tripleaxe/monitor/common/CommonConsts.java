package com.tripleaxe.monitor.common;

/**
 * constants definition
 *
 * @author Tao
 */
public interface CommonConsts {
    
    /**
     * thread pool size
     */
    int COMMON_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
}
