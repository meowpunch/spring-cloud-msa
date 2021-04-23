package com.meowailand.bootiful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meowailand.bootiful.config.CustomProps;
import com.meowailand.bootiful.config.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageRestController {
    @Value("${custom.message:Hello Nothing}")
    private String message;

    private final CustomProps customProps;

    private final Environment environment;

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(MessageRestController.class);

    public MessageRestController(CustomProps customProps, Environment environment, HttpClient httpClient, ObjectMapper objectMapper) {
        this.customProps = customProps;
        this.environment = environment;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
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
                httpClient.getRestTemplate().exchange(baseUrl + "/health/check", HttpMethod.GET, null, String.class);
        return responseEntity.getBody();
    }
///*
//
//{"pageNum":"1","sortKey":"companyName","isDesc":"0","searchOnMgmt":"false","searchStr":"앤톡","prevSearchStr":"","prevKeyword":"false","organizationSeq":0,"areaParamList":[],"industryParamList":[],"corpTypeParamList":[],"certficationParam":{​"ventureCertified":false,"innobizCertified":false,"mainbizCertified":false,"sovenCertified":false,"resovenCertified":false}​,"scrnParamList":[]}​'
// */
//    @GetMapping("/test2")
//    String test2() {
//        final String baseUrl = environment.getProperty("custom.testUrl");
//        assert baseUrl != null;
//
//        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
//        parameters.add("pageNum", 1);
//        parameters.add("sortKey", "앤톡");
//        parameters.add("isDesc", 0);
//        parameters.add("searchOnMgmt": "false");
//        para
//
//        httpClient.getRestTemplate().postForObject(baseUrl + "screener/list", );
//        RequestEntity requestEntity = new RequestEntity();
//        ResponseEntity<String> responseEntity =
//                httpClient.getRestTemplate().exchange(baseUrl + "screener/list", HttpMethod.POST, null, String.class);
//        return responseEntity.getBody();
//    }
}

