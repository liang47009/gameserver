package com.yunfeng.game.socket;

import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.util.Log;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

@Sharable
public class ByteServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        Log.d("ByteServerHandler userEventTriggered: " + ctx.channel().id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d("ByteServerHandler channelActive: " + ctx.channel().id());
        DispatcherManager.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.d("ByteServerHandler channelInactive: " + ctx.channel().id());
        DispatcherManager.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if (cause instanceof IOException) {
            Log.e("ByteServerHandler exceptionCaught" + cause.getMessage());
        } else {
            Log.e("ByteServerHandler exceptionCaught", cause);
            cause.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        DispatcherManager.dipatch(ctx, msg);
    }

}
