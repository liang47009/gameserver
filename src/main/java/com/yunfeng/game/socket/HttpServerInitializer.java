package com.yunfeng.game.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private SslContext sslCtx;

    @Resource(name = "httpServerHandler")
    private ChannelHandler mCustomHandler;

    public void init(boolean useSSL) {
        // Configure SSL.
        if (useSSL) {
            try {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(),
                        ssc.privateKey()).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sslCtx = null;
        }
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(mCustomHandler);
    }
}
