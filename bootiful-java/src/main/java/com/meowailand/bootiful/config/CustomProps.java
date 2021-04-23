package com.meowailand.bootiful.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("custom")
public class CustomProps {
    private String message;
}

