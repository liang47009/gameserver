package com.yunfeng.game.transfer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DataTransfer {
	private int len;
	private byte mid;
	private byte pid;
	private IData data;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte getMid() {
		return mid;
	}

	public void setMid(byte mid) {
		this.mid = mid;
	}

	public byte getPid() {
		return pid;
	}

	public void setPid(byte pid) {
		this.pid = pid;
	}

	public IData getData() {
		return data;
	}

	public void setData(IData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("mid=");
		sb.append(mid);
		sb.append(", pid=");
		sb.append(pid);
		sb.append(", data=");
		sb.append(data.toString());
		return sb.toString();
	}

	public IData readData(ByteBuf msg) {
		int length = msg.readInt();
		ByteBuf buff = msg.readBytes(length);
		StringData data = new StringData();
		data.setLength(length);
		data.readBuff(buff);
		return data;
	}

	public ByteBuf toByteBuff() {
		ByteBuf buff = Unpooled.buffer(this.getLen());
		buff.writeInt(this.getLen());
		buff.writeByte(this.getMid());
		buff.writeByte(this.getPid());
		data.writeBuff(buff);
		return buff;
	}

}
