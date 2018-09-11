package com.yunfeng.game.processor.httpcode;

import com.yunfeng.game.processor.IHttpProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class IndexProcessor implements IHttpProcessor {

    private final String ret = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "\t<head>\n"
            + "\t<title>Welcome to nginx!</title>\n"
            + "\t<style>\n"
            + "\t    body {\n"
            + "\t        width: 35em;\n"
            + "\t        margin: 0 auto;\n"
            + "\t        font-family: Tahoma, Verdana, Arial, sans-serif;\n"
            + "\t    }\n"
            + "\t</style>\n"
            + "\t</head>\n"
            + "\t<body>\n"
            + "\t<h1>Welcome to nginx!</h1>\n"
            + "\t<p>If you see this page, the nginx web server is successfully installed and\n"
            + "\tworking. Further configuration is required.</p>\n"
            + "\n"
            + "\t<p>For online documentation and support please refer to\n"
            + "\t<a href=\"http://nginx.org/\">nginx.org</a>.<br/>\n"
            + "\tCommercial support is available at\n"
            + "\t<a href=\"http://nginx.com/\">nginx.com</a>.</p>\n"
            + "\n" + "\t<p><em>Thank you for using nginx.</em></p>\n"
            + "\t</body>\n" + "</html>";

    @Override
    public void process(ChannelHandlerContext ctx, HttpRequest request, String param) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(ret.getBytes());
        ctx.writeAndFlush(response);
        ctx.close();
    }
}
