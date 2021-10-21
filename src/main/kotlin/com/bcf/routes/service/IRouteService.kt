package com.bcf.routes.service

interface IRouteService {
    fun route(origin: String, destination: String): List<String>?
}