package com.yunfeng.game.dispatcher;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DispatcherManager {

    private static Executor executor = Executors.newCachedThreadPool();

    private static List<IDispatcher> dispatchers = new ArrayList<>();

    public static boolean registerDispatcher(IDispatcher dispatcher) {
        return dispatchers.add(dispatcher);
    }

    public static void dipatch(final ChannelHandlerContext ctx, final Object msg) {
        executor.execute(() -> {
            for (IDispatcher dispatch : dispatchers) {
                if (dispatch.dipatch(ctx, msg)) {
                    break;
                }
            }
        });
    }

    public static void channelActive(final ChannelHandlerContext ctx) {

    }

    public static void channelInactive(ChannelHandlerContext ctx) {

    }

}
