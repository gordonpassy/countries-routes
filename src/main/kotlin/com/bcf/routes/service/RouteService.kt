package com.bcf.routes.service

import org.springframework.stereotype.Service

@Service
class RouteService: IRouteService {
    override fun route(origin: String, destination: String): List<String>? {
        return null
    }
}