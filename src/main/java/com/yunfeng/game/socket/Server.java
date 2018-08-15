package com.yunfeng.game.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private ChannelHandler serverHandler = new ByteServerHandler();

    private ChannelInitializer serverInitializer = new ByteServerInitializer(serverHandler);

    public void startUp(final String host, final int port) {
        System.setProperty("io.netty.noPreferDirect", "true");
        System.setProperty("io.netty.noUnsafe", "true");

        new Thread(() -> {
            try {
                new Server().run(host, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void run(String host, int port) throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(serverInitializer);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.SO_BACKLOG, 128);
        b.bind(host, port).sync().channel().closeFuture().sync();
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
