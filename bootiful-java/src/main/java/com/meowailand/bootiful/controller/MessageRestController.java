package com.meowailand.bootiful.controller;

import com.meowailand.bootiful.config.CustomProps;
import com.meowailand.bootiful.config.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageRestController {
    @Value("${custom.message:Hello Nothing}")
    private String message;

    private final CustomProps customProps;

    private final Environment environment;

    private final HttpClient httpClient;

    private final Logger logger = LoggerFactory.getLogger(MessageRestController.class);

    public MessageRestController(CustomProps customProps, Environment environment, HttpClient httpClient) {
        this.customProps = customProps;
        this.environment = environment;
        this.httpClient = httpClient;
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

    @GetMapping("/test")
    String test() {
        final String baseUrl = environment.getProperty("custom.testUrl");
        assert baseUrl != null;
        ResponseEntity<String> responseEntity =
                httpClient.getRestTemplate().exchange(baseUrl, HttpMethod.GET, null, String.class);
        return responseEntity.getBody();
    }
}
