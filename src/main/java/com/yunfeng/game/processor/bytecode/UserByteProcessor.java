package com.yunfeng.game.processor.bytecode;

import com.yunfeng.game.processor.IByteProcessor;
import com.yunfeng.game.transfer.DataTransfer;
import io.netty.channel.ChannelHandlerContext;

public class UserByteProcessor implements IByteProcessor {

    @Override
    public void process(ChannelHandlerContext ctx, DataTransfer request) {
        // Log.e("UserByteProcessor: " + request);
        DataTransfer response = new DataTransfer();
        response.setMid((byte) 1);
        response.setPid((byte) 2);
        response.setResponse(buff -> {
            String msg = "Hello client!";
            buff.writeInt(msg.length());
            buff.writeBytes(msg.getBytes());
        });
        ctx.writeAndFlush(response);
    }

}
