package com.mori.dubbo.core.listener;

import com.alibaba.dubbo.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 环境变量监听器 处理环境变量参数
 */
@Slf4j
@Component
public class ApplicationEnvironmentPreparedEventListener
		implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	private static final String APPLICATION_PROPERTIES_NAME="applicationConfig: [classpath:/application.properties]";

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment env = event.getEnvironment();
		MutablePropertySources propertySources = env.getPropertySources();
//		env.getPropertySources().
		OriginTrackedMapPropertySource applicationProperties=null;
		Iterator<PropertySource<?>> iterator = propertySources.iterator();
		while(iterator.hasNext()) {
			PropertySource<?> next = iterator.next();
			if(next.getName().equals(APPLICATION_PROPERTIES_NAME)){
				log.info("------读取拦截配置文件 替换环境变量-----"+next.getClass());
				 applicationProperties=(OriginTrackedMapPropertySource)next;
				Map<String,Object> sourceMap=applicationProperties.getSource();
				Map<String,Object> sourceMapCopy= new HashMap<>();
				sourceMap.forEach((k,v)->{
					OriginTrackedValue value= (OriginTrackedValue)sourceMap.get(k) ;
					OriginTrackedValue newvalue=OriginTrackedValue.of(this.replaceEnvConfig(value.getValue().toString()),value.getOrigin());
					sourceMapCopy.put(k,newvalue);
				});
				applicationProperties=new OriginTrackedMapPropertySource(APPLICATION_PROPERTIES_NAME,sourceMapCopy);
			}
		}
		env.getPropertySources().replace(APPLICATION_PROPERTIES_NAME,applicationProperties);
		log.debug("打印环境变量：{}", env);
	}

	private String replaceEnvConfig(String value) {

		String REG="^.{0,}\\$\\{.{0,}[/:].{0,}\\}.{0,}";
		if(value.matches(REG)){
			int firstKeyIdx=value.indexOf("${");
			int lastKeyIdx=value.indexOf("}");
			String preValue=value.substring(0,firstKeyIdx);
			String tailValue=value.substring(lastKeyIdx+1);
			String cutValue=value.substring(firstKeyIdx+2,lastKeyIdx);
			int firstSpiltkey=cutValue.indexOf(":");
			String envKey=cutValue.substring(0,firstSpiltkey);
			String envDefault=cutValue.substring(firstSpiltkey+1);
			String envValue=System.getenv(envKey);

			return preValue+(StringUtils.isBlank(envValue)?envDefault:envValue)+tailValue;
		}else{
			return value;
		}
	}

}
