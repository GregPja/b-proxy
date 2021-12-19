package b.proxy.service

import b.proxy.BoulderUser
import b.proxy.api.dto.SlotRequest
import b.proxy.service.entity.SlotInfo
import java.time.OffsetDateTime

interface BoulderingService {
    val name: String
    fun getFreeSlots(from: OffsetDateTime, to: OffsetDateTime): Collection<SlotInfo>
    fun bookSlot(user: BoulderUser, slotInfo: SlotRequest)
}
