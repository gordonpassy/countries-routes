package com.bcf.routes.service

import com.bcf.routes.remote.client.Countries
import com.bcf.routes.remote.client.CountriesAPIClient
import com.bcf.routes.remote.client.CountriesItem

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
        log.info("method=route request={}-{}", origin, destination)

        val countries: Countries    = countriesApiClient.fetchCountries()
        val originCountryData       = countries.filter { country -> country.cca3 == origin }

        val originCountry = if (originCountryData.isEmpty())
            return null
        else originCountryData.first()

        if (originCountry.borders.isNullOrEmpty())
            return null

        if (originCountry.borders.contains(destination))
            return listOf(origin, destination)

        val mappedBorders = mutableSetOf<String>()
        val path          = mutableListOf<String>()
        val result        = mutableListOf<List<String>>()
        val rootNode      = Node(origin)

        mapBorders(rootNode, mappedBorders, destination, countries)
        findAllRoutes(rootNode, path, result, destination)

        log.info("method=route result={}", result)
        return if (result.isEmpty())
            null
        else
            result.sortedWith( compareBy { it.size }).first()
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

    private fun mapBorders(parent: Node<String>, mappedBorders: MutableSet<String>, destination: String, countries: Countries){
        mappedBorders.add(parent.value)
        val country: CountriesItem = countries.first { country -> country.cca3 == parent.value }
        val borders: List<String>? = country.borders
        if (borders.isNullOrEmpty())
            return
        else
            borders.filter { border -> border !in mappedBorders }.forEach {
                val childNode = Node(it)
                parent.addChild(childNode)
            }

        for (node in parent.children) {
            if (node.value == destination){
                continue
            }
            mapBorders(node, mappedBorders, destination, countries)
        }
    }

    private fun findAllRoutes(root: Node<String>, path: MutableList<String>, result: MutableList<List<String>>, destination: String): MutableList<List<String>> {
        path.add(root.value)
        if (!root.hasChildren()){
            if (path.contains(destination))
                result.add(path.map { it })
            path.removeLast()
        } else {
            for (child in root.children){
                findAllRoutes(child, path, result, destination)
            }
            path.removeLast()
        }
        return result
    }
}