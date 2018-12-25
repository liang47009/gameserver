package com.yunfeng.game.socket;

import io.netty.channel.ChannelInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ByteServer extends Server {

    @Resource(name = "byteServerInitializer")
    private ChannelInitializer serverInitializer;

    @Override
    protected boolean init() {

        return true;
    }

    @Override
    public ChannelInitializer getServerInitializer() {
        return serverInitializer;
    }

}
