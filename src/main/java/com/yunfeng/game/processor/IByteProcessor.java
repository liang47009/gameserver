package com.yunfeng.game.processor;

public interface IByteProcessor<T> extends IProcessor {

    void process(T request);

}
