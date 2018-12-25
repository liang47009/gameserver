package com.yunfeng.game.dispatcher;

import com.yunfeng.game.processor.bytecode.ByteCodeModuleManager;
import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ByteDispatcher implements IDispatcher {

    @Resource
    private ByteCodeModuleManager byteCodeModuleManager;

    @Override
    public boolean dipatch(ChannelHandlerContext ctx, Object msg) {
        // TODO Auto-generated method stub
        boolean processable = false;
        if (msg instanceof DataTransfer) {
            DataTransfer request = (DataTransfer) msg;
            processable = byteCodeModuleManager.handleMessage(ctx, request);
        } else if (msg instanceof ByteBuf) {
            StringBuilder log = new StringBuilder("ByteBuf msg: ");
            ByteBuf buf = (ByteBuf) msg;
            byte[] arr = buf.array();
            for (byte val : arr) {
                String hex = Integer.toHexString(val & 0xFF);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                log.append(hex);
            }
            Log.d(log.toString());
        }
        return processable;
    }

}
