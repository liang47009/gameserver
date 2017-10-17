package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.util.Log;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		AttributeKey<MemoryData> userdata = AttributeKey.valueOf(ctx.channel()
				.id().asLongText());
		Attribute<MemoryData> attr = ctx.channel().attr(userdata);

		MemoryData data = new MemoryData();
		data.setLoginTime(System.currentTimeMillis() + "");

		attr.set(data);
		Log.d("channelActive: " + ctx.channel().id());
		Log.d("data: " + data + ", time=" + data.getLoginTime());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		AttributeKey<MemoryData> userdata = AttributeKey.valueOf(ctx.channel()
				.id().asLongText());
		Attribute<MemoryData> attr = ctx.channel().attr(userdata);
		MemoryData data = attr.get();
		Log.d("channelInactive: " + ctx.channel().id());
		Log.d("data: " + data + ", time=" + data.getLoginTime());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// super.exceptionCaught(ctx, cause);
		// System.err.println("exceptionCaught");
		if (cause instanceof ReadTimeoutException) {
			Log.e("ReadTimeoutException");
		} else if (cause instanceof WriteTimeoutException) {
			Log.e("WriteTimeoutException");
		} else {
			Log.e("exceptionCaught: " + cause.getMessage());
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
