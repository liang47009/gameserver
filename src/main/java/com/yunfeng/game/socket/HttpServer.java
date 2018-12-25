package com.yunfeng.game.socket;

import io.netty.channel.ChannelInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HttpServer extends Server {

    @Resource
    private HttpServerInitializer httpServerInitializer;

    @Override
    protected boolean init() {
        httpServerInitializer.init(false);
        return true;
    }

    @Override
    public ChannelInitializer getServerInitializer() {
        return httpServerInitializer;
    }
}
