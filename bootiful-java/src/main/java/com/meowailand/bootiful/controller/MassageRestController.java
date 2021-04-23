package com.meowailand.bootiful.controller;

import com.meowailand.bootiful.config.CustomProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Test @RefreshScope with @Value, @ConfigurationProperties, core.env.Environment
 */
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
