package com.yunfeng.game;

import com.yunfeng.game.socket.ByteServer;
import com.yunfeng.game.socket.HttpServer;
import com.yunfeng.game.util.SpringUtils;
import org.springframework.beans.FatalBeanException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        ByteServer byteServer = SpringUtils.getBean("byteServer", ByteServer.class);
        byteServer.startUp("172.19.34.44", 8888);
        HttpServer httpServer = SpringUtils.getBean("httpServer", HttpServer.class);
        httpServer.startUp("172.19.34.44", 8080);
//        new ByteServer().startUp("172.19.34.44", 8888);
    }
}
