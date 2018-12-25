package com.yunfeng.game.dispatcher;

import com.yunfeng.game.processor.IProcessor;

public interface IRegister<T> {
    boolean register(T msgId, IProcessor processor);

    boolean unRegister(T msgId);
}
