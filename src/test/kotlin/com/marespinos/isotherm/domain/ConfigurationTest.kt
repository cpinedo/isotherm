package com.marespinos.isotherm.domain

import com.marespinos.isotherm.domain.valueobjects.TemperatureMax
import com.marespinos.isotherm.domain.valueobjects.TemperatureMin
import io.kotlintest.specs.BehaviorSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class ConfigurationTest : BehaviorSpec({
    Given("A configuration") {
        lateinit var configuration: Configuration
        When("The temperature max is greater than temperature min") {
            val temperatureMin = 2000
            val temperatureMax = 2200
            Then("There shouldn't be any exception") {
                configuration = Configuration.of(TemperatureMin.of(temperatureMin), TemperatureMax.of(temperatureMax))
                assertEquals(configuration.temperatureMax, 2200)
                assertEquals(configuration.temperatureMin, 2000)
            }
        }
        When("The temperature min is greater than temperature max") {
            val temperatureMin = 2200
            val temperatureMax = 2000
            Then("A TemperatureConfigurationException should be thrown") {
                assertThrows<Configuration.TemperatureConfigurationException> { configuration = Configuration.of(TemperatureMin.of(temperatureMin), TemperatureMax.of(temperatureMax)) }
            }
        }
    }
}) {
}