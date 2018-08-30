package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

public class HttpServer extends Server {

    public HttpServer() {
        ChannelHandler serverHandler = new HttpServerHandler();
        ChannelInitializer serverInitializer = new HttpServerInitializer(false, serverHandler);
        setServerInitializer(serverInitializer);
    }
}
