package com.antock.bootiful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BootifulKotlinApplication

fun main(args: Array<String>) {
    runApplication<BootifulKotlinApplication>(*args)
}
