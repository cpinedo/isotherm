package com.marespinos.isotherm.infrastructure.rest

import com.marespinos.isotherm.application.getconfiguration.GetConfigurationCommand
import com.marespinos.isotherm.application.gettemperature.GetTemperatureCommand
import com.marespinos.isotherm.application.services.configuration.ConfigurationData
import com.marespinos.isotherm.application.setconfiguration.SetConfigurationCommand
import com.marespinos.isotherm.framework.CommandExecutor
import com.marespinos.isotherm.framework.Request
import com.marespinos.isotherm.infrastructure.configuration.ConfigurationResponse
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReadingResponse
import io.kotlintest.specs.BehaviorSpec
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.concurrent.CompletableFuture

class TemperatureResourceTest : BehaviorSpec({
    val commandExecutor = mockk<CommandExecutor>(relaxed = true)
    val target = TemperatureResource(commandExecutor)
    val mockMvc = MockMvcBuilders.standaloneSetup(target).build()

    Given("A get temperature request") {
        When("The temperature is [0, 10]") {
            val handlerResponse: CompletableFuture<TemperatureReadingResponse> = CompletableFuture.supplyAsync {
                TemperatureReadingResponse(listOf(0.0, 10.0), 5.0)
            }

            val slot = slot<Request>()
            every {
                commandExecutor.executeCommand(capture(slot))
            } returns handlerResponse
            Then("A get temperature command should be sent to the commandBus") {
                val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/temperature"))
                        .andExpect(MockMvcResultMatchers.request().asyncStarted())
                        .andDo(MockMvcResultHandlers.log())
                        .andReturn()

                mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.temperatures.[0]", Matchers.`is`(0.0)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.temperatures.[1]", Matchers.`is`(10.0)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.mean", Matchers.`is`(5.0)))

                Assertions.assertTrue(slot.isCaptured)
                Assertions.assertTrue(slot.captured is GetTemperatureCommand)
            }
        }
    }
}) {
}