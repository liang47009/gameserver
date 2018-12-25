package com.yunfeng.game.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Threads {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
