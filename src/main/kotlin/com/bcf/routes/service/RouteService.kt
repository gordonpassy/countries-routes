package com.bcf.routes.service

import com.bcf.routes.remote.client.CountriesAPIClient

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RouteService: IRouteService {

    val log: Logger = LoggerFactory.getLogger(RouteService::class.java)

    @Autowired
    lateinit var countriesApiClient: CountriesAPIClient

    override fun route(origin: String, destination: String): List<String>? {
        return null
    }

    data class Node<T>(var value: T) {
        var parent: Node<T>? = null
        var children: MutableList<Node<T>> = mutableListOf()

        fun addChild(node: Node<T>) {
            children.add(node)
            node.parent = this
        }

        fun hasChildren(): Boolean =
            children.size >= 1
    }
}