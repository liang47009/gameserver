package com.yunfeng.game.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// Add SSL handler first to encrypt and decrypt everything.
		// In this example, we use a bogus certificate in the server side
		// and accept any invalid certificates in the client side.
		// You will need something more complicated to identify both
		// and server in the real world.
		//
		// Read SecureChatSslContextFactory
		// if you need client certificate authentication.
		// SSLEngine engine = SslContextFactory.getServerContext()
		// .createSSLEngine();
		// engine.setUseClientMode(false);
		// pipeline.addLast("ssl", new SslHandler(engine));

		// On top of the SSL handler, add the text line codec.
		// pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
		// Delimiters.lineDelimiter()));
		pipeline.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
		pipeline.addLast("decoder", new ByteDecoder());
		pipeline.addLast("encoder", new ByteEncoder());
		// pipeline.addLast("decoder", new HtmlDecoder());
		// pipeline.addLast("encoder", new HtmlEncoder());
		// and then business logic.
		pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
		pipeline.addLast("writeTimeoutHandler", new WriteTimeoutHandler(30));
		pipeline.addLast("idleStateHandler", new IdleStateHandler(5, 3, 0));
		pipeline.addLast("idleHandler", new IdleHandler());
		pipeline.addLast("handler", new ServerHandler());
	}

}
