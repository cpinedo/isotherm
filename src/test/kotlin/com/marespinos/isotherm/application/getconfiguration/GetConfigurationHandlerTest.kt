package com.marespinos.isotherm.application.getconfiguration

import com.marespinos.isotherm.application.services.configuration.ConfigurationData
import com.marespinos.isotherm.infrastructure.configuration.ConfigurationAdaptor
import com.marespinos.isotherm.infrastructure.repository.ConfigurationEntity
import com.marespinos.isotherm.infrastructure.repository.ConfigurationRepository
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import java.util.*

class GetConfigurationHandlerTest : BehaviorSpec({
    val configurationRepository = mockk<ConfigurationRepository>(relaxed = true)
    val configurationService = ConfigurationAdaptor(configurationRepository)
    val getConfigurationHandler = GetConfigurationHandler(configurationService)
    Given("A get configuration command") {
        val command = GetConfigurationCommand()
        When("the existing configuration is min 20 and max 22") {
            val configurationEntity = ConfigurationEntity()
            configurationEntity.temperatureMin = 20
            configurationEntity.temperatureMax = 22
            every {
                configurationRepository.findById(any())
            } returns Optional.of(configurationEntity)
            Then("the existing configuration should be retrieved") {
                val result: ConfigurationData = getConfigurationHandler.handle(command).join()
                assertNotNull(result)
                assertEquals(result.maxTemperature,22)
                assertEquals(result.minTemperature,20)
            }
        }
    }
}) {
}