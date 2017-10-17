package com.yunfeng.game.processor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class UserProcessor implements IHttpProcessor {

	public void process() {
	}

	public void processUserData(int id, long lid, String username,
			String password) {

	}

	@Override
	public void process(ChannelHandlerContext ctx, HttpRequest request,
			String param) {
		// TODO Auto-generated method stub

	}

}
