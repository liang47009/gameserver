package com.yunfeng.game.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private static final ExecutorService pool = Executors.newFixedThreadPool(8);

    public static void submit(Runnable runnable) {
        pool.submit(runnable);
    }
}
