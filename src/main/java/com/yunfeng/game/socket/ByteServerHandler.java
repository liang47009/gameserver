package com.yunfeng.game.socket;

import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.util.Log;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ChannelHandler.Sharable
public class ByteServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        Log.d("ByteServerHandler userEventTriggered: " + ctx.channel().id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Log.d("ByteServerHandler channelActive: " + ctx.channel().id());
        DispatcherManager.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Log.d("ByteServerHandler channelInactive: " + ctx.channel().id());
        DispatcherManager.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            Log.e("ByteServerHandler exceptionCaught" + cause.getMessage());
        } else {
            Log.e("ByteServerHandler exceptionCaught", cause);
            cause.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        DispatcherManager.dipatch(ctx, msg);
    }

}
