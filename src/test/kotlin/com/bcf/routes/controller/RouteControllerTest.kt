package com.bcf.routes.controller

import com.bcf.routes.service.IRouteService

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import org.springframework.http.HttpStatus

class RouteControllerTest {
    @Mock
    lateinit var routeService: IRouteService

    @InjectMocks
    lateinit var routeController: RouteController

    @BeforeEach
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Returns HTTP 400 if there is no land crossing`() {
        Mockito.`when`(routeService.route("KEN", "USA")).thenReturn(null)

        val result = routeController.route("KEN", "USA")

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }

    @Test
    fun `Returns single route if there is land crossing`() {
        val route = listOf("KEN", "UGA", "RWA")

        Mockito.`when`(routeService.route("KEN", "UGA")).thenReturn(route)

        val result = routeController.route("KEN", "RWA")

        assertEquals(route, result.body?.route)
    }
}