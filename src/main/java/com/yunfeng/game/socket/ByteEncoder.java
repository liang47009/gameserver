package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.yunfeng.game.transfer.DataTransfer;

public class ByteEncoder extends MessageToMessageEncoder<DataTransfer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, DataTransfer msg,
			List<Object> out) throws Exception {
		out.add(msg.toByteBuff());
	}

}
