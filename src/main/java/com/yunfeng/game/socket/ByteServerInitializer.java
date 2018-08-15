package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ByteServerInitializer extends ChannelInitializer<SocketChannel> {

    private ChannelHandler mCustomHandler;

    public ByteServerInitializer(ChannelHandler coreHandler) {
        this.mCustomHandler = coreHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast("bytecodec", new ByteServerCodec());
        // and then business logic.
        pipeline.addLast("handler", mCustomHandler);
    }

}
