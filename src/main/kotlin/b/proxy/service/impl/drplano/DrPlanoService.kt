package b.proxy.service.impl.drplano

import b.proxy.BoulderUser
import b.proxy.api.dto.SlotRequest
import b.proxy.service.BoulderingService
import b.proxy.service.impl.drplano.client.DrPlanoClient
import b.proxy.service.impl.drplano.client.dto.DrPlanoBookingRequest
import b.proxy.service.impl.drplano.client.dto.DrPlanoSlotInfo
import java.time.OffsetDateTime

class DrPlanoService(
    private val drPlanoClient: DrPlanoClient,
    override val name: String,
    private val clientId: Int,
    private val shiftModelId: Int,
    private val urbanSportClubTariffId: Int,
    private val origin: String
) : BoulderingService {
    override fun getFreeSlots(from: OffsetDateTime, to: OffsetDateTime): Collection<DrPlanoSlotInfo> {
        return getAllSlots(from, to)
            .filter { it.state == "BOOKABLE" }
    }

    private fun getAllSlots(from: OffsetDateTime, to: OffsetDateTime): Collection<DrPlanoSlotInfo> {
        return drPlanoClient.getAllSlotsBetween(from, to, shiftModelId)
            .filter { it.start.isAfter(from.minusMinutes(1)) && it.start.isBefore(to.plusMinutes(1)) }
    }

    override fun bookSlot(user: BoulderUser, slotInfo: SlotRequest) {
        val slot = getAllSlots(
            from = slotInfo.start.minusHours(1), // just want to be sure to hit this dude
            to = slotInfo.end.plusHours(1)
        ).find { it.id == slotInfo.id } ?: throw Exception("Slot not found :( ")
        if (slot.state != "BOOKABLE") {
            throw Exception("Slot exists, but is not bookable anymore")
        }

        val bookingRequest = DrPlanoBookingRequest(
            user = user,
            clientId = clientId,
            shiftModelId = shiftModelId,
            shiftSelector = slot.selector,
            urbanSportClubTariffId = urbanSportClubTariffId
        )
        if (drPlanoClient.canBook(bookingRequest)) {
            drPlanoClient.reserve(bookingRequest)
            drPlanoClient.book(bookingRequest, origin).also {
                println(it.statusCode)
                println(String(it.content))
                println(String(it.request.body))
                if(it.statusCode > 203){
                    throw Exception("Ops, couldn't book because we got not 200")
                }
            }
        }
    }
}