package com.marespinos.isotherm.application.gettemperature

import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReaderAdaptor
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReaderFromDisk
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReadingResponse
import com.marespinos.isotherm.infrastructure.repository.TemperatureSnapshotRepository
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.stream.Stream

class GetTemperatureHandlerTest : BehaviorSpec({
    val temperatureSnapshotRepository = mockk<TemperatureSnapshotRepository>(relaxed = true)
    val temperatureReaderFromDisk = mockk<TemperatureReaderFromDisk>(relaxed = true)
    val temperatureReader = TemperatureReaderAdaptor(temperatureSnapshotRepository, temperatureReaderFromDisk)
    val getTemperatureHandler = GetTemperatureHandler(temperatureReader)
    Given("A get temperature command") {
        val command = GetTemperatureCommand()
        When("the temperature readings are 92 and 93.5") {
            val readings: Stream<Double> = listOf(92.0, 93.5).stream()
            every {
                temperatureReaderFromDisk.readTemperature(any())
            } returns readings
            every {
                temperatureSnapshotRepository.save(any())
            } returns null

            val result: TemperatureReadingResponse = getTemperatureHandler.handle(command).join()
            Then("the proper temperature should be retrieved") {
                assertEquals(result.rawTemperatures.size, 2)
                assertEquals(result.rawTemperatures[0], 92.0)
                assertEquals(result.rawTemperatures[1], 93.5)
                assertEquals(result.mean, 92.75)
            }
            And("A snapshot should be saved in the database") {
                verify(exactly = 1) {
                    temperatureSnapshotRepository.save(any())
                }
            }
        }
    }
}) {
}