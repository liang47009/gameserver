package com.yunfeng.game.logic.processor;

import com.yunfeng.game.processor.IByteProcessor;
import com.yunfeng.game.transfer.DataTransfer;
import io.netty.channel.ChannelHandlerContext;

public class UserByteProcessor implements IByteProcessor<DataTransfer> {

    @Override
    public void process(DataTransfer request) {
        // Log.e("UserByteProcessor: " + request);
        DataTransfer response = new DataTransfer();
        response.setMid((byte) 1);
        response.setPid((byte) 2);
        response.setResponse(buff -> {
            String msg = "Hello client!";
            buff.writeInt(msg.length());
            buff.writeBytes(msg.getBytes());
        });
        request.getChannelHandlerContext().writeAndFlush(response);
    }

}
