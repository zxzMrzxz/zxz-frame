package com.jingdianjichi.tool;

import org.slf4j.Logger;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ChickenWing
 * @Description: 异步future工具类封装
 * @DateTime: 2023/1/8 22:24
 */
public class CompletableFutureUtils {

    public static <T> T getResult(Future<T> future, long timeout, TimeUnit timeUnit, T defaultValue, Logger logger) {
        try {
            return future.get(timeout, timeUnit);
        } catch (Exception e) {
            logger.error("get future error,defaultValue:{}", defaultValue, e);
            return defaultValue;
        }
    }

}
