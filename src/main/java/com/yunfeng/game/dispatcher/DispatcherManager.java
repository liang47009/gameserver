package com.yunfeng.game.dispatcher;

import com.yunfeng.game.util.Threads;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class DispatcherManager {

    private static List<IDispatcher> dispatchers = new ArrayList<>();

    public static void registerDispatcher(IDispatcher dispatcher) {
        dispatchers.add(dispatcher);
    }

    public static void dipatch(final ChannelHandlerContext ctx, final Object msg) {
        Threads.execute(() -> {
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
