package b.proxy.service.entity

import java.time.OffsetDateTime

open class SlotInfo(
    val id: String,
    val start: OffsetDateTime,
    val end: OffsetDateTime,
    val freeSpots: Int,
)

