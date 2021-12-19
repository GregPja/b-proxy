package b.proxy.service.impl.drplano.client.dto

import b.proxy.service.entity.SlotInfo
import java.time.OffsetDateTime

data class DrPlanoSlotInfo(
    val selector: List<Any>,
    val state: String,
    val minCourseParticipantCount: Int,
    val maxCourseParticipantCount: Int,
    val currentCourseParticipantCount: Int,
    val dateList: List<DateRange>,
) : SlotInfo(
    start = dateList.first().start,
    end = dateList.first().end,
    id = selector.last() as String,// forgive me lord
    freeSpots = maxCourseParticipantCount - currentCourseParticipantCount,
)

data class DateRange(
    val start: OffsetDateTime,
    val end: OffsetDateTime
)