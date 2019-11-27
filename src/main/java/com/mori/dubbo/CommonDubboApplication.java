package com.mori.dubbo;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.mori.dubbo.core.listener.ApplicationEnvironmentPreparedEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alibaba.fastjson.JSON;
@Slf4j
@SpringBootApplication
@EnableDubbo
@DubboComponentScan("com.mori.dubbo.business")
public class CommonDubboApplication {

	public static void main(String[] args) {

		log.info("打印启动参数：{}", JSON.toJSONString(args));
		System.setProperty("file.encoding", "UTF-8");
		//class会重载，但不重启。与Tomcat的server.xml中的 reloadable="false" 效果一样
		SpringApplication application = new SpringApplication(CommonDubboApplication.class);
        application.addListeners(new ApplicationEnvironmentPreparedEventListener());
		application.run(args);
	}


}
