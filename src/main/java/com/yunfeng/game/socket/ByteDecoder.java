package com.yunfeng.game.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;

public class ByteDecoder extends MessageToMessageDecoder<ByteBuf> {

	private static final int MAX_LENGTH = 200;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		int avi = msg.readableBytes();
		if (avi > 4) {
			int length = msg.readInt();
			if ((msg.readableBytes() >= length) && (length > 0)
					&& (length < MAX_LENGTH)) {
				DataTransfer dt = new DataTransfer();
				dt.setMid(msg.readByte());
				dt.setPid(msg.readByte());
				dt.setData(dt.readData(msg));

				out.add(dt);
			} else {
				Log.e("length:" + length + ", decode:" + msg.readableBytes());
				msg.resetReaderIndex();
			}
		}
	}
}
