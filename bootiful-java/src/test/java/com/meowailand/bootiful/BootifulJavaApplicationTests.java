package com.meowailand.bootiful;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (
        properties = {
                "spring.cloud.config.enabled:false",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
        })
class BootifulJavaApplicationTests {

    @Test
    void contextLoads() {
    }

}
