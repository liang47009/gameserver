package com.yunfeng.tools.phoneproxy.socket;

import com.yunfeng.game.util.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.Security;

@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final class ServerToServerHandler extends
            ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            ctx.channel().writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            if (cause instanceof IOException) {
                Log.d("exceptionCaught" + cause.getMessage());
            } else {
                Log.e("exceptionCaught", cause);
                cause.printStackTrace();
            }
        }
    }

    private ChannelFuture cf;
    private String host;
    private int port;

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg)
            throws Exception {
        if (msg instanceof FullHttpRequest) {
            final FullHttpRequest request = (FullHttpRequest) msg;
            String hostdata = request.headers().get(HttpHeaderNames.HOST);
            if (null != hostdata) {
                String[] hostarr = hostdata.split(":");
                int port = 80;
                if (hostarr.length == 2) {
                    port = Integer.valueOf(hostarr[1]);
                }
                String host = hostarr[0];
                this.host = host;
                this.port = port;
            } else {
                InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
                        .remoteAddress();
                this.host = insocket.getHostName();
                this.port = insocket.getPort();
            }
            if ("CONNECT".equalsIgnoreCase(request.method().name())) {
                HttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                ctx.writeAndFlush(response);
                ctx.pipeline().remove("httpCodec");
                // ctx.pipeline().remove("httpObject");
                return;
            }
            Bootstrap b = new Bootstrap();
            b.group(ctx.channel().eventLoop())
                    .channel(ctx.channel().getClass())
                    .handler(new HttpProxyInitializer(ctx.channel()));
            // Make the connection attempt.
            cf = b.connect(host, port);
            cf.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future)
                        throws Exception {
                    // TODO Auto-generated method stub
                    if (future.isSuccess()) {
                        future.channel().writeAndFlush(msg);
                    } else {
                        ctx.channel().close();
                    }
                }
            });
        } else if (msg instanceof HttpContent) {

        } else if (msg instanceof ByteBuf) {
            ByteBuf bytebuf = (ByteBuf) msg;
            if (bytebuf.getByte(0) == 22) {

                Security.addProvider(new BouncyCastleProvider());
                InputStream trustIn = ServerHandler.class.getClassLoader()
                        .getResourceAsStream("client.bks");
                KeyStore ksTrust = KeyStore.getInstance("BKS", "BC");
                ksTrust.load(trustIn, "a123456".toCharArray());
                TrustManagerFactory tmf = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(ksTrust);

                SslContext sslContext = SslContextBuilder
                        .forServer(Cert.serverPriKey, Cert.getCert(this.host))
                        .trustManager(tmf).clientAuth(ClientAuth.NONE).build();
                ctx.pipeline().addFirst(new HttpServerCodec());
                ctx.pipeline().addFirst(sslContext.newHandler(ctx.alloc()));
                // 重新过一遍pipeline，拿到解密后的的http报文
                ctx.pipeline().fireChannelRead(msg);
            }
        } else {
            if (cf == null) {
                Bootstrap b = new Bootstrap();
                b.group(ctx.channel().eventLoop())
                        .channel(ctx.channel().getClass())
                        .handler(new ChannelInitializer<Channel>() {

                            @Override
                            protected void initChannel(Channel ch)
                                    throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                p.addLast("logging", new LoggingHandler(
                                        LogLevel.DEBUG));
                                p.addLast(new ServerToServerHandler());
                            }
                        });
                // Make the connection attempt.
                ChannelFuture cf = b.connect(host, port);
                cf.addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future)
                            throws Exception {
                        // TODO Auto-generated method stub
                        if (future.isSuccess()) {
                            future.channel().writeAndFlush(msg);
                        } else {
                            ctx.channel().close();
                        }
                    }
                });
            } else {
                cf.channel().writeAndFlush(msg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if (cause instanceof IOException) {
            Log.d("exceptionCaught" + cause.getMessage());
        } else {
            Log.e("exceptionCaught", cause);
            cause.printStackTrace();
        }
    }

}
