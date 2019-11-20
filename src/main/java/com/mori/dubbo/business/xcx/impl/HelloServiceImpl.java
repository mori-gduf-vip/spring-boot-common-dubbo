package com.mori.dubbo.business.xcx.impl;

import com.mori.dubbo.business.xcx.IHelloService;
import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass= IHelloService.class)
public class HelloServiceImpl implements IHelloService {
    @Override
    public void doHello() {

    }
}
