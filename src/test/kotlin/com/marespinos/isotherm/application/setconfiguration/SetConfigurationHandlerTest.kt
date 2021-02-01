package com.marespinos.isotherm.application.setconfiguration

import com.marespinos.isotherm.domain.Configuration
import com.marespinos.isotherm.infrastructure.configuration.ConfigurationAdaptor
import com.marespinos.isotherm.infrastructure.repository.ConfigurationEntity
import com.marespinos.isotherm.infrastructure.repository.ConfigurationRepository
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows

class SetConfigurationHandlerTest : BehaviorSpec({
    val configurationRepository = mockk<ConfigurationRepository>(relaxed = true)
    val configurationService = ConfigurationAdaptor(configurationRepository)
    val setConfigurationHandler = SetConfigurationHandler(configurationService)
    Given("A set configuration command with min=22 and max=25") {
        val command = SetConfigurationCommand(22, 25)
        When("the use case is invoked") {

            every {
                configurationRepository.save(any())
            } returns null

            setConfigurationHandler.handle(command).join()
            Then("the proper configuration should be persisted") {
                val entity = ConfigurationEntity()
                entity.id = 1
                entity.temperatureMin=22
                entity.temperatureMax=25
                verify(exactly = 1) {
                    configurationRepository.save(entity)
                }
            }
        }
    }
    Given("A set configuration command with min=23 and max=18") {
        val command = SetConfigurationCommand(23, 18)
        When("the use case is invoked") {
            Then("a validation exception should be thrown") {
                assertThrows<Configuration.TemperatureConfigurationException>{
                    setConfigurationHandler.handle(command).join()
                }
            }
        }
    }
}) {
}