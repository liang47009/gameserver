package com.yunfeng.game.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import com.yunfeng.game.processor.GameVersionProcessor;
import com.yunfeng.game.processor.GuestProcessor;
import com.yunfeng.game.processor.IHttpProcessor;
import com.yunfeng.game.processor.UserLoginProcessor;
import com.yunfeng.game.util.Log;

public class UriDispatcher implements IDispatcher {

	private Map<String, IHttpProcessor> processors = new HashMap<String, IHttpProcessor>();

	public UriDispatcher() {
		register("/get_serverinfo", new GameVersionProcessor());
		register("/guest", new GuestProcessor());
		register("/login", new UserLoginProcessor());
	}

	public void register(String uri, IHttpProcessor processor) {
		processors.put(uri, processor);
	}

	public boolean dipatch(ChannelHandlerContext ctx, Object msg) {
		// TODO Auto-generated method stub
		boolean processable = false;
		if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			String[] datas = request.uri().split("\\?");
			String uri = datas[0];
			String paramStr = "";
			if (datas.length == 2) {
				paramStr = datas[1];
			}
			IHttpProcessor processor = processors.get(uri);
			if (processor == null) {
				String log = "no processor found for: " + request.uri();
				Log.d(log);
			} else {
				processor.process(ctx, request, paramStr);
			}
			processable = true;
		}
		return processable;
	}

}
