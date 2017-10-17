package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;

import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.handler.ChannelHandler;
import com.yunfeng.game.util.Log;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ChannelHandler.getInstance().register(ctx.channel());
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ChannelHandler.getInstance().unRegister(ctx.channel());
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// super.exceptionCaught(ctx, cause);
		// System.err.println("exceptionCaught");
		if (cause instanceof ReadTimeoutException) {
			Log.d("ReadTimeoutException");
		} else if (cause instanceof WriteTimeoutException) {
			Log.d("WriteTimeoutException");
		}
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (DispatcherManager.dipatch(ctx, msg)) {
			// already ok
		} else {
			Log.d("no dispatcher found for class:" + msg);
		}
	}

}
