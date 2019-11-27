package com.mori.dubbo.env;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
public class EnvUtilTest {


    @Test
    void contextLoads() {
        String value="zookeeper://${ZK_URL:127.0.0.1}:2181";
        String REG="^.{0,}\\$\\{.{0,}[/:].{0,}\\}.{0,}";
        if(value.matches(REG)){
            int firstKeyIdx=value.indexOf("${");
            int lastKeyIdx=value.indexOf("}");
            String preValue=value.substring(0,firstKeyIdx);
            String tailValue=value.substring(lastKeyIdx+1);
            String cutValue=value.substring(firstKeyIdx+2,lastKeyIdx);
            int firstSpiltkey=cutValue.indexOf(":");
            String envKey=cutValue.substring(0,firstSpiltkey);
            String envDefault=cutValue.substring(firstSpiltkey);
            String envValue=System.getProperty(envKey);

            log.debug("?????:"+preValue+(StringUtils.isBlank(envValue)?envDefault:envValue)+tailValue); ;
        }else{
          log.info("?????:"+value);
        }
    }
}
