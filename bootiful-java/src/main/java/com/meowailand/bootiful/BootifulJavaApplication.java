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
import org.springframework.stereotype.Component;
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
        final String responseMessage = String.format(
                "Environment: %s \n" +
                        "Value: %s \n" +
                        "Config Props: %s \n"
                , environment.getProperty("custom.message"), this.message, customProps.getMessage());

        logger.info(responseMessage);
        return responseMessage;
    }
}

@RefreshScope
@RestController
class MassageRestController {
    @Value("${custom.message:Hello Nothing}")
    private String message;

    private final Logger logger = LoggerFactory.getLogger(MessageRestController.class);

    private final CustomProps customProps;

    private final Environment environment;

    public MassageRestController(CustomProps customProps, Environment environment) {
        this.customProps = customProps;
        this.environment = environment;
    }

    @GetMapping("/massage")
    String getMessage() {
        final String responseMessage = String.format(
                "Environment: %s \n" +
                        "Value: %s \n" +
                        "Config Props: %s \n"
                , environment.getProperty("custom.message"), this.message, customProps.getMessage());

        logger.info(responseMessage);
        return responseMessage;
    }
}

@Data
@Component
@ConfigurationProperties("custom")
class CustomProps {
    private String message;
}
