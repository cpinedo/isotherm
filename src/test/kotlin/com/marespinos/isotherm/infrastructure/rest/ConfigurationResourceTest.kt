package com.marespinos.isotherm.infrastructure.rest

import com.marespinos.isotherm.application.getconfiguration.GetConfigurationCommand
import com.marespinos.isotherm.application.services.configuration.ConfigurationData
import com.marespinos.isotherm.application.setconfiguration.SetConfigurationCommand
import com.marespinos.isotherm.framework.CommandExecutor
import com.marespinos.isotherm.framework.Request
import com.marespinos.isotherm.infrastructure.configuration.ConfigurationResponse
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch

import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.concurrent.CompletableFuture


class ConfigurationResourceTest : BehaviorSpec({
    val commandExecutor = mockk<CommandExecutor>(relaxed = true)
    val target = ConfigurationResource(commandExecutor)
    val mockMvc = MockMvcBuilders.standaloneSetup(target).build()

    Given("A get configuration request") {
        When("The configuration is max=10 and min=0") {
            val handlerResponse: CompletableFuture<ConfigurationData> = CompletableFuture.supplyAsync {
                ConfigurationResponse(10, 0)
            }

            val slot = slot<Request>()
            every {
                commandExecutor.executeCommand(capture(slot))
            } returns handlerResponse
            Then("A get configuration command should be sent to the commandBus") {
                val mvcResult: MvcResult = mockMvc.perform(get("/configuration"))
                        .andExpect(request().asyncStarted())
                        .andDo(MockMvcResultHandlers.log())
                        .andReturn()

                mockMvc.perform(asyncDispatch(mvcResult))
                        .andExpect(status().isOk)
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.maxTemperature", `is`(10)))
                        .andExpect(jsonPath("$.minTemperature", `is`(0)))

                assertTrue(slot.isCaptured)
                assertTrue(slot.captured is GetConfigurationCommand)
            }
        }
    }

    Given("A post configuration request") {
        When("The post configuration request is min=50 and max=200") {
            val handlerResponse: CompletableFuture<Void> = CompletableFuture.supplyAsync { null }

            val slot = slot<Request>()
            every {
                commandExecutor.executeCommand(capture(slot))
            } returns handlerResponse
            Then("A post configuration command should be sent to the commandBus") {
                val mvcResult: MvcResult = mockMvc.perform(post("/configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maxTemperature\": 200,\"minTemperature\": 50}"))
                        .andReturn()

                mockMvc.perform(asyncDispatch(mvcResult))
                        .andExpect(status().isOk)

                assertTrue(slot.isCaptured)
                assertTrue(slot.captured is SetConfigurationCommand)
                assertEquals((slot.captured as SetConfigurationCommand).maxTemp, 200)
                assertEquals((slot.captured as SetConfigurationCommand).minTemp, 50)
            }
        }
    }
}) {
}