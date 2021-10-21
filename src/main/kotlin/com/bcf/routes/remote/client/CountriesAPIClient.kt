package com.bcf.routes.remote.client

import com.bcf.routes.config.objectMapper
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import feign.Response
import feign.codec.Decoder

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import java.lang.reflect.Type

@FeignClient(value = "countries", url = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json")
interface CountriesAPIClient {
    @GetMapping(consumes = ["text/plain"])
    fun fetchCountries(): Countries
}

class CountriesDecoder: Decoder {
    override fun decode(p0: Response?, p1: Type?): Any {
        return  objectMapper.readValue(p0!!.body().asInputStream(), Countries::class.java)
    }
}

class Countries : ArrayList<CountriesItem>()

@JsonIgnoreProperties(ignoreUnknown = true)
data class CountriesItem(
    @JsonProperty("area")
    val area: Double?,
    @JsonProperty("borders")
    val borders: List<String>?,
    @JsonProperty("capital")
    val capital: List<String>?,
    @JsonProperty("cca2")
    val cca2: String?,
    @JsonProperty("cca3")
    val cca3: String,
    @JsonProperty("ccn3")
    val ccn3: String?,
    @JsonProperty("cioc")
    val cioc: String?,
    @JsonProperty("independent")
    val independent: Boolean?,
    @JsonProperty("landlocked")
    val landlocked: Boolean?,
    @JsonProperty("latlng")
    val latlng: List<Double>?,
    @JsonProperty("name")
    val name: Name,
    @JsonProperty("region")
    val region: String?,
    @JsonProperty("status")
    val status: String?,
    @JsonProperty("subregion")
    val subregion: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Name(
    @JsonProperty("common")
    val common: String?,
    @JsonProperty("official")
    val official: String?
)