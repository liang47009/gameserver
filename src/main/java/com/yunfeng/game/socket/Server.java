package com.yunfeng.game.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.yunfeng.game.dispatcher.DispatcherManager;

public class Server {

	public void startUp(final String host, final int port) {
		System.setProperty("io.netty.noPreferDirect", "false");
		System.setProperty("io.netty.noUnsafe", "false");

		// AppUtils.init();
		DispatcherManager.init();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Server().run(host, port);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void run(String host, int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerInitializer serverInitializer = new ServerInitializer();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(serverInitializer);
			b.option(ChannelOption.TCP_NODELAY, false);
			b.bind(host, port).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
