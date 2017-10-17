package com.yunfeng.game.transfer;

import io.netty.buffer.ByteBuf;

public class StringData implements IData {

	private int length;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return msg;
	}

	public void readBuff(ByteBuf buff) {
		byte[] arr = new byte[length];
		buff.readBytes(arr);
		msg = new String(arr);
	}

	@Override
	public void writeBuff(final ByteBuf buff) {
		buff.writeInt(length);
		buff.writeBytes(msg.getBytes());
	}

}
