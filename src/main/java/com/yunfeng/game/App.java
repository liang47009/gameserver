package com.yunfeng.game;

import com.yunfeng.game.dispatcher.ByteDispatcher;
import com.yunfeng.game.dispatcher.DispatcherManager;
import com.yunfeng.game.dispatcher.UriDispatcher;
import com.yunfeng.game.processor.bytecode.UserByteProcessor;
import com.yunfeng.game.processor.httpcode.*;
import com.yunfeng.game.socket.HttpServer;
import com.yunfeng.game.socket.Server;

/**
 * Hello world!
 */
public class App {

    private static final byte USER_PROCESSOR_MODULEID = 1;

    public static void main(String[] args) throws Exception {

        final UriDispatcher uriDispatcher = new UriDispatcher();
        uriDispatcher.register("/", new IndexProcessor());
        uriDispatcher.register("/favicon.ico", new FaviconProcessor());
        uriDispatcher.register("/get_serverinfo", new GameVersionProcessor());
        uriDispatcher.register("/guest", new GuestProcessor());
        uriDispatcher.register("/login", new UserLoginProcessor());
        DispatcherManager.registerDispatcher(uriDispatcher);

        final ByteDispatcher byteDispatcher = new ByteDispatcher();
        byteDispatcher.register(USER_PROCESSOR_MODULEID, new UserByteProcessor());
        DispatcherManager.registerDispatcher(byteDispatcher);

        Server server = new HttpServer();
        server.startUp("172.19.34.25", 8888);
    }
}
// 2017-10-09T05:56:42.688805Z 1 [Note] A temporary password is generated for
// root@localhost: ddGWplwl#6Pa
//
// If you lose this password, please consult the section How to Reset the Root
// Password in the MySQL reference manual.