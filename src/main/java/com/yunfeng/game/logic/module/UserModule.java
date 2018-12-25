package com.yunfeng.game.logic.module;

import com.yunfeng.game.logic.processor.UserByteProcessor;
import com.yunfeng.game.processor.IByteProcessor;
import com.yunfeng.game.processor.IModule;
import com.yunfeng.game.transfer.DataTransfer;

import java.util.HashMap;
import java.util.Map;

public class UserModule implements IModule<DataTransfer> {

    private static final byte USER_HELLO_CLIENT = 23;

    private Map<Byte, IByteProcessor<DataTransfer>> processors = new HashMap<>(8);

    public void registerProcessor(byte pid, IByteProcessor<DataTransfer> processor) {
        processors.put(pid, processor);
    }

    public UserModule() {
        registerProcessor(USER_HELLO_CLIENT, new UserByteProcessor());
    }

    @Override
    public boolean handlerMessage(DataTransfer msg) {
        IByteProcessor<DataTransfer> processor = processors.get(msg.getPid());
        boolean processed = false;
        if (processor != null) {
            processor.process(msg);
            processed = true;
        } else {
            System.err.println("no process found for pid: " + msg.getPid());
        }
        return processed;
    }
}
