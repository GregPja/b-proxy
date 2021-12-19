package b.proxy.service.impl.drplano.client

import b.proxy.configurations.HttpClient
import b.proxy.service.impl.drplano.client.dto.DrPlanoBookingRequest
import b.proxy.service.impl.drplano.client.dto.DrPlanoSlotInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import khttp.responses.Response
import java.time.OffsetDateTime

interface DrPlanoClient {

    fun getAllSlotsBetween(from: OffsetDateTime, to: OffsetDateTime, id: Int): Collection<DrPlanoSlotInfo>
    fun canBook(drPlanoBookingRequest: DrPlanoBookingRequest): Boolean
    fun reserve(drPlanoBookingRequest: DrPlanoBookingRequest): Boolean
    fun book(bookingRequest: DrPlanoBookingRequest, origin: String): Response
    fun bookOption(origin: String): Response
}

class DrPlanoClientImpl(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper
) : DrPlanoClient {
    private val baseUrl = "https://backend.dr-plano.com/"

    override fun getAllSlotsBetween(from: OffsetDateTime, to: OffsetDateTime, id: Int): Collection<DrPlanoSlotInfo> {
        return httpClient.get(
            url = baseUrl + "courses_dates",
            params = mapOf(
                "id" to id,
                "advanceToFirstMonthWithDates" to "",
                "start" to from.toInstant().toEpochMilli(),
                "end" to to.toInstant().toEpochMilli(),
            ),
            headers = mapOf("User-Agent" to "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:94.0) Gecko/20100101 Firefox/94.0")
        ).let {
            objectMapper.readValue<List<DrPlanoSlotInfo>>(
                it.content
            )
        }
    }

    override fun canBook(drPlanoBookingRequest: DrPlanoBookingRequest): Boolean {
        return when (httpClient.option(
            url = baseUrl + "courses_reserve",
            params = mapOf(
                "shiftModelId" to drPlanoBookingRequest.shiftModelId,
                "email" to drPlanoBookingRequest.participants.first().email,
                "shiftSelector" to drPlanoBookingRequest.shiftSelector,
                "participantCount" to drPlanoBookingRequest.participants.size
            )
        ).statusCode) {
            200 -> true
            else -> false
        }
    }

    override fun reserve(drPlanoBookingRequest: DrPlanoBookingRequest): Boolean {
        return when (httpClient.post(
            url = baseUrl + "courses_reserve",
            params = mapOf(
                "shiftModelId" to drPlanoBookingRequest.shiftModelId,
                "email" to drPlanoBookingRequest.participants.first().email,
                "shiftSelector" to drPlanoBookingRequest.shiftSelector,
                "participantCount" to drPlanoBookingRequest.participants.size
            )
        ).statusCode) {
            200 -> true
            else -> false
        }
    }

    override fun book(bookingRequest: DrPlanoBookingRequest, origin: String): Response {
        return httpClient.post(
            url = baseUrl + "bookable",
            body = objectMapper.writeValueAsString(bookingRequest),
            headers = mapOf(
                "Origin" to origin,
                "Referer" to "$origin/",
                "Host" to "backend.dr-plano.com",
                "Accept-Language" to "en-US,en;q=0.5"
            )
        )
    }

    override fun bookOption(origin: String): Response {
        return httpClient.option(
            url = baseUrl + "bookable",
            headers = mapOf("Origin" to origin, "Referer" to "$origin/")
        )
    }

}