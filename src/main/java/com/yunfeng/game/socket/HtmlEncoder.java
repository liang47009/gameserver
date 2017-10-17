package com.yunfeng.game.socket;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HtmlEncoder extends HttpResponseEncoder {
	@Override
	protected void encodeInitialLine(ByteBuf buf, HttpResponse response)
			throws Exception {
		super.encodeInitialLine(buf, response);
	}
}
