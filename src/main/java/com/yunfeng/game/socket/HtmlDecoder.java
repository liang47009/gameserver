package com.yunfeng.game.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequestDecoder;

import java.util.List;

public class HtmlDecoder extends HttpRequestDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		super.decode(ctx, msg, out);
	}
}
