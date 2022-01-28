package b.proxy.service.impl.webclimber

import b.proxy.service.entity.SlotInfo
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Component
class WebClimberDataExtractor {
    private val slotsRegex = "(\\d\\d:\\d\\d)\\s+-\\s+(\\d\\d:\\d\\d)".toRegex()
    private val dateRegex = "date=(\\d\\d\\d\\d-\\d\\d-\\d\\d)".toRegex()
    private val freeSpotsRegex = "(\\d+) ".toRegex()
    fun getSlot(rowElement: Element): SlotInfo? {
        val columns = rowElement.select("td")
        if (columns.size < 3) {
            return null;
        }
        val (startHour, endHours) = slotsRegex.find(columns[0].ownText())!!.destructured
        val availableSlots = freeSpotsRegex.find(columns[1].ownText())!!.destructured.component1().toInt()


        val buttonElement = columns[2].selectFirst("button")

        val id = buttonElement!!.id()
        val time = dateRegex.find(buttonElement.attributes().get("data-url"))!!.destructured.component1()
        return SlotInfo(
            id = id,
            start = OffsetDateTime.of(
                LocalDateTime.parse(
                    "$time $startHour",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                ), ZoneOffset.UTC
            ),
            end = OffsetDateTime.of(
                LocalDateTime.parse(
                    "$time $endHours",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                ), ZoneOffset.UTC
            ),
            freeSpots = availableSlots
        )
    }

    fun getToken(document: Document): BookingSession {
        return BookingSession(
            token = document.select("input[name=YII_CSRF_TOKEN]").`val`(),
            session = document.select("input[name=webclimber_session]").`val`()
        )
    }

    fun getNewBookingUrl(parsedContent: Document): String {
        return parsedContent.select("div[id=breadcrumb]")
            .select("a")
            .find { "Termin" in it.text() }!!
            .attr("href")
    }
}