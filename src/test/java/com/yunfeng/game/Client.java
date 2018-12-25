package com.yunfeng.game;

import com.yunfeng.game.util.AlgorithmUtils;
import com.yunfeng.tools.phoneproxy.socket.ByteCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Client {

    public static final Random r = new Random(10000);

    private static final int TOTAL = 10000;

    public static void main(String[] args) {
        // String host = "::1";
        CountDownLatch latch = new CountDownLatch(TOTAL);
        for (int i = 0; i < TOTAL; i++) {
            latch.countDown();
//            System.out.print("client: " + i + ", ");
            new Thread(() -> {
                String host = "172.19.34.44";
                int port = 8888;
                try {
                    int dd = r.nextInt();
                    String msg = "socket connect, " + ", Random:" + dd;
//                    System.out.println("  dd:" + dd + ";");
//                    ByteBuf buff = Unpooled.buffer(msg.length());
//                    buff.writeInt(msg.length() + 2);
//                    buff.writeByte(1);
//                    buff.writeByte(23);
//                    buff.writeBytes(msg.getBytes());
//                    byte[] buffer = buff.array();
//                    buff.release();

                    byte[] buffer = new byte[msg.length() + 4 + 1 + 1];
                    byte[] intdata = AlgorithmUtils.intToByteArray(msg.length());
                    int index = 0;
                    for (byte anIntdata1 : intdata) {
                        buffer[index] = anIntdata1;
                        index++;
                    }
                    buffer[index] = 1;
                    index++;
                    buffer[index] = 23;
                    index++;
                    intdata = msg.getBytes();
                    for (byte anIntdata : intdata) {
                        buffer[index] = anIntdata;
                        index++;
                    }

                    Socket s = new Socket();

                    latch.await();
                    s.connect(new InetSocketAddress(host, port));
                    OutputStream os = s.getOutputStream();
                    os.write(buffer);
                    os.flush();
                    InputStream is = s.getInputStream();
                    byte[] b = new byte[1024];
                    int ri = is.read(b);
                    System.out.println("is.read: " + new String(b));
                    is.close();
                    os.close();
                    s.close();
//                    new Client().start(host, port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // for (int i = 0; i < 5; i++) {
        // new Thread(new Runnable() {
        // @Override
        // public void run() {
        // try {
        // new Client().start(host, port);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // }).start();
        // }
    }

    public void start(String host, int port) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.option(ChannelOption.TCP_NODELAY, false);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.group(workerGroup).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                // TODO Auto-generated method stub
                ChannelPipeline pipe = ch.pipeline();
                // pipe.addLast("frameDecoder", new
                // LengthFieldBasedFrameDecoder(
                // Integer.MAX_VALUE, 0, 4, 0, 4));
                // pipe.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipe.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
                pipe.addLast("bytecodec", new ByteCodec());
                pipe.addLast("handler", new ClientHandler());
            }
        });
        // b.group(bossGroup, workerGroup)
        // .channel(NioServerSocketChannel.class)
        // .childHandler(serverInitializer);
        // b.conn(host, port).sync().channel().closeFuture().sync();
        b.connect(host, port).sync().channel();
    }
}
