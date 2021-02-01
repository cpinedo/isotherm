package com.marespinos.isotherm.domain

import com.marespinos.isotherm.domain.services.pluginteractor.PlugInteractor
import com.marespinos.isotherm.domain.valueobjects.TemperatureMax
import com.marespinos.isotherm.domain.valueobjects.TemperatureMin
import io.kotlintest.specs.BehaviorSpec
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class IsothermTest : BehaviorSpec({
    Given("A configuration with min=20 and max=22 degrees") {
        var configuration: Configuration = Configuration.of(TemperatureMin.of(2000), TemperatureMax.of(2200))
        When("the reading is above 22 degrees") {
            val temperatureInstantData = TemperatureInstantData.of(listOf(22.toDouble(), 25.toDouble()))
            And("the switch status is on") {
                val switchStatus = true
                Then("The switch should be turned off") {
                    val plugInteractor = mockk<PlugInteractor>(relaxed = true)
                    val isotherm = Isotherm()
                    assertTrue(isotherm.doMaintenance(configuration, temperatureInstantData, plugInteractor, switchStatus))
                    verify(exactly = 1) {
                        plugInteractor.turnSwitchOff()
                    }
                }
            }
            And("the switch status is off") {
                val switchStatus = false
                Then("The switch should't be turned off") {
                    val plugInteractor = mockk<PlugInteractor>(relaxed = true)
                    val isotherm = Isotherm()
                    assertFalse(isotherm.doMaintenance(configuration, temperatureInstantData, plugInteractor, switchStatus))
                    verify(exactly = 0) {
                        plugInteractor.turnSwitchOff()
                    }
                }
            }
        }
        When("the reading is below 20 degrees") {
            val temperatureInstantData = TemperatureInstantData.of(listOf(17.toDouble(), 20.toDouble()))
            And("the switch status is on") {
                val switchStatus = true
                Then("The switch shouldn't be turned on") {
                    val plugInteractor = mockk<PlugInteractor>(relaxed = true)
                    val isotherm = Isotherm()
                    assertFalse(isotherm.doMaintenance(configuration, temperatureInstantData, plugInteractor, switchStatus))
                    verify(exactly = 0) {
                        plugInteractor.turnSwitchOn()
                    }
                }
            }
            And("the switch status is off") {
                val switchStatus = false
                Then("The switch should be turned on") {
                    val plugInteractor = mockk<PlugInteractor>(relaxed = true)
                    val isotherm = Isotherm()
                    assertTrue(isotherm.doMaintenance(configuration, temperatureInstantData, plugInteractor, switchStatus))
                    verify(exactly = 1) {
                        plugInteractor.turnSwitchOn()
                    }
                }
            }
        }
        When("the reading is between 20 and 22 degrees") {
            val temperatureInstantData = TemperatureInstantData.of(listOf(20.toDouble(), 22.toDouble()))
            Then("The switch status shouldn't change regardless the status of the switch") {
                val plugInteractor = mockk<PlugInteractor>(relaxed = true)
                val isotherm = Isotherm()
                assertFalse(isotherm.doMaintenance(configuration, temperatureInstantData, plugInteractor, true))
                assertFalse(isotherm.doMaintenance(configuration, temperatureInstantData, plugInteractor, false))
                verify(exactly = 0) {
                    plugInteractor.turnSwitchOn()
                }
                verify(exactly = 0) {
                    plugInteractor.turnSwitchOff()
                }
            }
        }

    }
}) {}
