package b.proxy.api.dto

import b.proxy.service.UserSlotBookingRequest
import b.proxy.service.entity.SlotInfo
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

data class SlotInfoDTO(
    val id: String,
    val start: OffsetDateTime,
    val end: OffsetDateTime,
    val freeSpots: Int
) {

    constructor(slotInfo: SlotInfo) : this(
        id = slotInfo.id,
        start = slotInfo.start,
        end = slotInfo.end,
        freeSpots = slotInfo.freeSpots,
    )
}

data class SlotBookingRequestDTO(
    val id: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val start: OffsetDateTime,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val end: OffsetDateTime,
    val place: String,
    @JsonProperty(value = "user_id")
    val userId: Long
) {
    fun toEntity(): UserSlotBookingRequest {
        return UserSlotBookingRequest(
            id, start, end, userId, place
        )
    }
}


open class SlotRequest(
    val id: String,
    val start: OffsetDateTime,
    val end: OffsetDateTime
)