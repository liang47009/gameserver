package com.yunfeng.game;

import com.yunfeng.game.socket.ByteServer;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        new ByteServer().startUp("172.19.34.25", 8888);
    }
}
