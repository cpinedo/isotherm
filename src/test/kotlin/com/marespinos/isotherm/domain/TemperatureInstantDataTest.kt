package com.marespinos.isotherm.domain

import io.kotlintest.specs.BehaviorSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

class TemperatureInstantDataTest : BehaviorSpec({
    Given("A temperature reading") {
        lateinit var temperatureInstantData: TemperatureInstantData
        When("both temperatures are the same") {
            val reading = listOf(2000.toDouble(), 2000.toDouble())
            Then("the mean should be the value of any of the temperatures") {
                temperatureInstantData = TemperatureInstantData.of(reading)
                assertEquals(temperatureInstantData.mean, reading[0])
                assertEquals(temperatureInstantData.mean, reading[1])
                assertEquals(temperatureInstantData.rawTemperatures.size, 2)
            }
        }
        When("both temperatures differs a bit") {
            val reading = listOf(2001.toDouble(), 2000.toDouble())
            Then("the mean should be different from any of the temperatures") {
                temperatureInstantData = TemperatureInstantData.of(reading)
                assertNotEquals(temperatureInstantData.mean, reading[0])
                assertNotEquals(temperatureInstantData.mean, reading[1])
            }
            Then("the mean should be properly calculated") {
                temperatureInstantData = TemperatureInstantData.of(reading)
                assertEquals(temperatureInstantData.mean, 2000.5.toDouble())
            }
        }
        When("both temperatures differs a lot") {
            val reading = listOf(4000.toDouble(), 2000.toDouble())
            Then("the mean should be different from any of the temperatures") {
                temperatureInstantData = TemperatureInstantData.of(reading)
                assertNotEquals(temperatureInstantData.mean, reading[0])
                assertNotEquals(temperatureInstantData.mean, reading[1])
            }
            Then("the mean should be properly calculated") {
                temperatureInstantData = TemperatureInstantData.of(reading)
                assertEquals(temperatureInstantData.mean, 3000.toDouble())
            }
        }
    }
}) {
}