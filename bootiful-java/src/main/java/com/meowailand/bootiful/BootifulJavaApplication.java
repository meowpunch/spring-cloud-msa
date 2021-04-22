package com.meowailand.bootiful;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BootifulJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulJavaApplication.class, args);
    }
}

@RefreshScope
@RestController
class MessageRestController {
    @Value("${custom.message:Hello Nothing}")
    private String message;

    private final CustomProps customProps;

    private final Environment environment;

    private final Logger logger = LoggerFactory.getLogger(MessageRestController.class);

    public MessageRestController(CustomProps customProps, Environment environment) {
        this.customProps = customProps;
        this.environment = environment;
    }

    @GetMapping("/message")
    String getMessage() {
        logger.info("/message Endpoint 가 호출됐습니다.");

        return String.format(
            "Environment: %s \n" +
            "Value: %s \n" +
            "Config Props: %s \n"
        , environment.getProperty("custom.message"), this.message, customProps.getMessage());
    }
}

@Data
@Configuration
@ConfigurationProperties("custom")
class CustomProps {
    private String message;
}
