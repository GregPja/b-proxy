package b.proxy.service.impl.webclimber

import b.proxy.BoulderUser
import b.proxy.api.dto.SlotRequest
import b.proxy.service.BoulderingService
import b.proxy.service.entity.SlotInfo
import java.time.OffsetDateTime

class WebClimberService(
    override val name: String,
    private val webClimberClient: WebClimberClient,
    private val webClimberId: Int,
    private val specialPath: String,
) : BoulderingService {
    override fun getFreeSlots(from: OffsetDateTime, to: OffsetDateTime): Collection<SlotInfo> {
        return webClimberClient.getAllAvailableSlotsFor(from, webClimberId, specialPath)
    }

    override fun bookSlot(user: BoulderUser, slotInfo: SlotRequest) {

        //val token = webClimberClient.getSessionData(slotInfo.start, specialPath, webClimberId)
    }
}