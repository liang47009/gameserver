package com.yunfeng.game.dispatcher;

import com.yunfeng.game.processor.httpcode.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class RegisterManager {

    @Resource
    private ByteDispatcher byteDispatcher;
    @Resource
    private UriDispatcher uriDispatcher;

    @PostConstruct //初始化方法的注解方式  等同与init-method=init
    public void init() {
        initByteDispatcher();
        initUriDispatcher();

        DispatcherManager.registerDispatcher(byteDispatcher);
        DispatcherManager.registerDispatcher(uriDispatcher);
    }

    private void initUriDispatcher() {
        uriDispatcher.register("/", new IndexProcessor());
        uriDispatcher.register("/favicon.ico", new FaviconProcessor());
        uriDispatcher.register("/get_serverinfo", new GameVersionProcessor());
        uriDispatcher.register("/guest", new GuestProcessor());
        uriDispatcher.register("/login", new UserLoginProcessor());
    }

    private void initByteDispatcher() {

    }

    @PreDestroy //销毁方法的注解方式  等同于destory-method=destory222
    public void destory() {
        System.out.println("调用销毁方法....");
    }

}
