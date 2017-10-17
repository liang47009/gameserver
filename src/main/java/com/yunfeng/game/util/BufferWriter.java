package com.yunfeng.game.util;

import io.netty.buffer.ByteBuf;

public class BufferWriter {

	public static int writeInt(int i, ByteBuf buf) {
		buf.writeInt(i);
		return 4;
	}

	public static int writeLong(long lid, ByteBuf buf) {
		buf.writeLong(lid);
		return 8;
	}

	public static int writeString(String username, ByteBuf buf) {
		int length = 0;
		if (null != username) {
			byte[] bs = username.getBytes();
			length = bs.length + 4;
			buf.writeInt(bs.length);
			buf.writeBytes(bs);
		}
		return length;
	}

}
