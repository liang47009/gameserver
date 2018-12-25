package com.yunfeng.game.socket;

import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.util.Log;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ChannelHandler.Sharable
public class HttpServerHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        Log.d("userEventTriggered: " + ctx.channel().id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Log.d("channelActive: " + ctx.channel().id());
        DispatcherManager.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Log.d("channelInactive: " + ctx.channel().id());
        DispatcherManager.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            Log.e("exceptionCaught" + cause.getMessage());
        } else {
            Log.e("exceptionCaught", cause);
            cause.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        DispatcherManager.dipatch(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }
}
