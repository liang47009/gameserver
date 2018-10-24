package com.yunfeng.game.socket;

import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.dispatcher.UriDispatcher;
import com.yunfeng.game.processor.httpcode.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

public class HttpServer extends Server {

    @Override
    protected boolean init() {
        final UriDispatcher uriDispatcher = new UriDispatcher();
        uriDispatcher.register("/", new IndexProcessor());
        uriDispatcher.register("/favicon.ico", new FaviconProcessor());
        uriDispatcher.register("/get_serverinfo", new GameVersionProcessor());
        uriDispatcher.register("/guest", new GuestProcessor());
        uriDispatcher.register("/login", new UserLoginProcessor());
        DispatcherManager.registerDispatcher(uriDispatcher);

        ChannelHandler serverHandler = new HttpServerHandler();
        ChannelInitializer serverInitializer = new HttpServerInitializer(false, serverHandler);
        setServerInitializer(serverInitializer);

        return true;
    }
}
