package com.yunfeng.game.processor;

import com.yunfeng.game.transfer.DataTransfer;
import io.netty.channel.ChannelHandlerContext;

public interface IByteProcessor extends IProcessor {

    void process(ChannelHandlerContext ctx, DataTransfer request);

}
