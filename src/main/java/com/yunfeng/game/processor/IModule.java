package com.yunfeng.game.processor;

public interface IModule<T> {
    boolean handlerMessage(T msg);
}
