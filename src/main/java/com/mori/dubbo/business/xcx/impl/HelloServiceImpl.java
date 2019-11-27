package com.mori.dubbo.business.xcx.impl;

import com.mori.dubbo.business.xcx.IHelloService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(version = "1.0",interfaceClass= IHelloService.class)
public class HelloServiceImpl implements IHelloService {
    @Override
    public void doHello() {
        log.error("-------");
    }
}
