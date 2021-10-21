package com.bcf.routes.service

import com.bcf.routes.config.objectMapper
import com.bcf.routes.remote.client.Countries
import com.bcf.routes.remote.client.CountriesAPIClient

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import org.springframework.core.io.ClassPathResource

import java.io.InputStream

class RouteServiceTest {
    @Mock
    lateinit var client: CountriesAPIClient

    @InjectMocks
    lateinit var routeService: RouteService

    private val countries: Countries = objectMapper.readValue(inputStream(), Countries::class.java)

    @BeforeEach
    fun init() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(client.fetchCountries()).thenReturn(countries)
    }

    @Test
    fun `returns null if origin country does not exist`(){
        val result = routeService.route("MTV", "ITA")
        assertNull(result)
    }

    @Test
    fun `returns null if origin country has no borders`() {
        val result  = routeService.route("VCT", "ITA")
        assertNull(result)
    }

    @Test
    fun `returns null if there's no land crossing between origin and destination`() {
        val result = routeService.route("KEN", "USA")
        assertNull(result)
    }

    @Test
    fun `returns route if destination country is within origin countries borders`() {
        val result  = routeService.route("VAT", "ITA")
        assertNotNull(result)
        assertEquals("VAT", result?.first())
        assertEquals("ITA", result?.last())
    }

    @Test
    fun `returns route if land crossing is possible between origin country and destination country`() {
        val result = routeService.route("KEN", "RWA")
        assertNotNull(result)
        assertEquals("KEN", result?.first())
        assertEquals("RWA", result?.last())
    }

    private fun inputStream(): InputStream {
        val resource = ClassPathResource("countries.json")
        return resource.inputStream
    }
}