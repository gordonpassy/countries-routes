package com.bcf.routes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RoutesApplication

fun main(args: Array<String>) {
    runApplication<RoutesApplication>(*args)
}
