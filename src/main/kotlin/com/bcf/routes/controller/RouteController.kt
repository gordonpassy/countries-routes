package com.bcf.routes.controller

import com.bcf.routes.service.IRouteService
import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.Optional

@RestController
@RequestMapping
@Tag(name = "Routes")
class RouteController {
    @Autowired
    lateinit var routeService: IRouteService

    @GetMapping(path = ["/routing/{origin}/{destination}"])
    fun route(@PathVariable origin: String, @PathVariable destination: String): ResponseEntity<Route> {

        val route = routeService.route(origin, destination)

        return if (route.isNullOrEmpty())
             ResponseEntity(HttpStatus.BAD_REQUEST)
        else
            ResponseEntity.of(Optional.of(Route(route)))
    }

    data class Route(
        val route: List<String>
    )
}