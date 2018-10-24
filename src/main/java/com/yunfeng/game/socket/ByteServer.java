package com.yunfeng.game.socket;

import com.yunfeng.game.dispatcher.ByteDispatcher;
import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.processor.bytecode.UserByteProcessor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

public class ByteServer extends Server {

    private static final byte USER_PROCESSOR_MODULEID = 1;

    @Override
    protected boolean init() {
        final ByteDispatcher byteDispatcher = new ByteDispatcher();
        byteDispatcher.register(USER_PROCESSOR_MODULEID, new UserByteProcessor());
        DispatcherManager.registerDispatcher(byteDispatcher);

        ChannelHandler serverHandler = new ByteServerHandler();
        ChannelInitializer serverInitializer = new ByteServerInitializer(serverHandler);
        setServerInitializer(serverInitializer);
        return true;
    }
}
