package com.yunfeng.game.processor.bytecode;

import com.yunfeng.game.logic.module.UserModule;
import com.yunfeng.game.processor.IModule;
import com.yunfeng.game.transfer.DataTransfer;
import com.yunfeng.game.util.Log;
import com.yunfeng.game.util.Threads;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ByteCodeModuleManager {

    private BlockingQueue<DataTransfer> queue = new LinkedBlockingQueue<>();

    private Map<Byte, IModule<DataTransfer>> modules = new HashMap<>();

    private static final byte USER_PROCESSOR_MODULEID = 1;

    public void register(byte moduleId, IModule<DataTransfer> module) {
        modules.put(moduleId, module);
    }

    @PostConstruct
    public void init() {

        register(USER_PROCESSOR_MODULEID, new UserModule());

        Threads.execute(() -> {
            while (true) {
                try {
                    DataTransfer dataTransfer = queue.take();
                    IModule<DataTransfer> module = modules.get(dataTransfer.getMid());
                    if (module != null) {
                        module.handlerMessage(dataTransfer);
                    } else {
                        Log.e("no module found for mid: " + dataTransfer.getPid());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean handleMessage(ChannelHandlerContext ctx, DataTransfer request) {
        request.setChannelHandlerContext(ctx);
        return queue.offer(request);
    }
}
