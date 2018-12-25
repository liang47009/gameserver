package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ByteServerInitializer extends ChannelInitializer<SocketChannel> {

    @Resource(name = "byteServerHandler")
    private ChannelHandler mCustomHandler;

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        //pipeline.addLast("logging", new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("bytecodec", new ByteServerCodec());
        // and then business logic.
        pipeline.addLast("handler", mCustomHandler);
    }

}
