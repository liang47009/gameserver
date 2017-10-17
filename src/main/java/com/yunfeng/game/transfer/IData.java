package com.yunfeng.game.transfer;

import io.netty.buffer.ByteBuf;

public interface IData {

	int getLength();

	void writeBuff(final ByteBuf buff);

}
