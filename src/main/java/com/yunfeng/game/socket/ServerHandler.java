package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
		Log.e("exceptionCaught", cause);
		cause.printStackTrace();
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
