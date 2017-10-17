package com.yunfeng.game.dispatcher;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class DispatcherManager {

	private static List<IDispatcher> dispatchers = new ArrayList<IDispatcher>();

	public static void init() {
		registerDispatcher(new UriDispatcher());
		registerDispatcher(new ByteDispatcher());
	}

	public static boolean registerDispatcher(IDispatcher dispatcher) {
		return dispatchers.add(dispatcher);
	}

	public static boolean dipatch(ChannelHandlerContext ctx, Object msg) {
		for (IDispatcher dispatch : dispatchers) {
			boolean b = dispatch.dipatch(ctx, msg);
			if (b) {
				return b;
			}
		}
		return false;
	}

}
