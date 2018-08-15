package com.yunfeng.game.processor.httpcode;

import com.yunfeng.game.util.Log;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.io.InputStream;

import com.yunfeng.game.processor.IHttpProcessor;

public class FaviconProcessor implements IHttpProcessor {

    private byte[] b = null;

    public FaviconProcessor() {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("favicon.ico");
            b = new byte[in.available()];
            int len = in.read(b);
            if (len == -1) {
                Log.e("read ico error!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void process(ChannelHandlerContext ctx, HttpRequest request,
                        String param) {
        // TODO Auto-generated method stub
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        ByteBuf src = Unpooled.buffer();
        src.writeBytes(b);
        response.content().writeBytes(src);
        ctx.writeAndFlush(response);
        ctx.close();
    }
}
