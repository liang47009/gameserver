package com.yunfeng.game.socket;

import com.yunfeng.game.util.Log;
import com.yunfeng.game.util.Threads;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.Future;

public abstract class Server {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    protected abstract boolean init();

    private void run(String host, int port) throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(getServerInitializer());
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.SO_BACKLOG, 128);
        b.bind(host, port).sync().channel().closeFuture().sync();
    }

    public void startUp(final String host, final int port) {
        System.setProperty("io.netty.noPreferDirect", "true");
        System.setProperty("io.netty.noUnsafe", "true");
        if (init()) {
            Future<?> future = Threads.submit(() -> {
                try {
                    run(host, port);
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Log.i("startup future: " + future);
//            try {
//                Object o = future.get();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//
//            }
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public abstract ChannelInitializer getServerInitializer();

}
