package com.yunfeng.game.socket;

import com.yunfeng.game.transfer.DataTransfer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

public final class ByteServerCodec extends CombinedChannelDuplexHandler<MessageToMessageDecoder<ByteBuf>, MessageToMessageEncoder<DataTransfer>> {

    public ByteServerCodec() {
        init(new ByteDecoder(), new ByteEncoder());
    }

    private final class ByteEncoder extends MessageToMessageEncoder<DataTransfer> {

        @Override
        protected void encode(ChannelHandlerContext ctx, DataTransfer msg, List<Object> out) {
            ByteBuf buff = Unpooled.wrappedBuffer(msg.toByteBuff().array());
            out.add(buff);
        }

    }

    private final class ByteDecoder extends MessageToMessageDecoder<ByteBuf> {
        // private static final int MAX_LENGTH = 1024;

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
            int avi = buffer.readableBytes();
            if (avi > 4) {
                int length = buffer.readInt();
                if ((buffer.readableBytes() >= length) && (length > 0)) {
                    ByteBuf buff = Unpooled.buffer(length);
                    DataTransfer dt = new DataTransfer();
                    dt.setMid(buffer.readByte());
                    dt.setPid(buffer.readByte());
                    buffer.readBytes(buff, 0, length - 2);
                    buffer.markReaderIndex();

                    dt.setData(buff);
                    out.add(dt);
                } else {
                    buffer.resetReaderIndex();
                    // ByteBuf buff = Unpooled.copiedBuffer(buffer);
                    // out.add(buff);// 转发给下一个codec
                }
            }
        }
    }
}
