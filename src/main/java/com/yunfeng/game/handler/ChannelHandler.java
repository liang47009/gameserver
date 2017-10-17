package com.yunfeng.game.handler;

import io.netty.channel.Channel;

import java.util.LinkedList;

import com.yunfeng.game.util.Log;

public class ChannelHandler {
	private ChannelHandler() {
	}

	private static final ChannelHandler instance = new ChannelHandler();

	public static ChannelHandler getInstance() {
		return instance;
	}

	// private List<Channel> channels = Collections
	// .synchronizedList(new ArrayList<Channel>());
	private LinkedList<Channel> channels = new LinkedList<Channel>();

	public void register(Channel channel) {
		// synchronized (channels) {
		channels.add(channel);
		// }
		Log.d("register:" + channels.size());
	}

	public void unRegister(Channel channel) {
		// synchronized (channels) {
		channels.remove(channel);
		// }
		Log.d("unRegister:" + channels.size());
	}

}
