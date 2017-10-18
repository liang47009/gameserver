package com.yunfeng.game.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;

public final class ByteServerCodec
		extends
		CombinedChannelDuplexHandler<MessageToMessageDecoder<ByteBuf>, MessageToMessageEncoder<DataTransfer>> {

	public ByteServerCodec() {
		init(new ByteDecoder(), new ByteEncoder());
	}

	private final class ByteEncoder extends
			MessageToMessageEncoder<DataTransfer> {

		@Override
		protected void encode(ChannelHandlerContext ctx, DataTransfer msg,
				List<Object> out) throws Exception {
			out.add(msg.toByteBuff());
		}

	}

	private final class ByteDecoder extends MessageToMessageDecoder<ByteBuf> {

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
					msg.resetReaderIndex();
					ByteBuf buff = Unpooled.copiedBuffer(msg);
					out.add(buff);// 转发给下一个codec
				}
			}
		}
	}
}
