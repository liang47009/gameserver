package com.yunfeng.game.dispatcher;

import com.yunfeng.game.processor.IHttpProcessor;
import com.yunfeng.game.util.Log;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;

public class UriDispatcher implements IDispatcher {

    private Map<String, IHttpProcessor> processors = new HashMap<String, IHttpProcessor>();

    public void register(String uri, IHttpProcessor processor) {
        processors.put(uri, processor);
    }

    public boolean dipatch(ChannelHandlerContext ctx, Object msg) {
        // TODO Auto-generated method stub
        boolean processable = false;
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String[] datas = request.uri().split("\\?");
            String uri = datas[0];
            String paramStr = "";
            if (datas.length == 2) {
                paramStr = datas[1];
            }
            IHttpProcessor processor = processors.get(uri);
            if (processor == null) {
                String log = "no processor found for: " + request.uri();
                Log.d(log);
            } else {
                processor.process(ctx, request, paramStr);
            }
            processable = true;
        } else if (msg instanceof LastHttpContent) {
            LastHttpContent request = (LastHttpContent) msg;
            if (request.content().capacity() == 0) {
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
                ctx.writeAndFlush(response);
                ctx.close();
                processable = true;
            }
        }
        return processable;
    }

}
