package com.yunfeng.game.processor.bytecode;

import io.netty.channel.ChannelHandlerContext;

import com.yunfeng.game.processor.IByteProcessor;
import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;

public class UserByteProcessor implements IByteProcessor {

	@Override
	public void process(ChannelHandlerContext ctx, DataTransfer request) {
		Log.d("UserByteProcessor: " + request);
	}

}
