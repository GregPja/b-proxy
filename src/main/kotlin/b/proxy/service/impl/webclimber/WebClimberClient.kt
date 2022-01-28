package b.proxy.service.impl.webclimber

import b.proxy.configurations.HttpClient
import b.proxy.service.entity.SlotInfo
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

interface WebClimberClient {
    fun getAllAvailableSlotsFor(date: OffsetDateTime, id: Int, specialPath: String): Collection<SlotInfo>
}
@Component
class WebClimberClientImpl(
    private val httpClient: HttpClient,
    private val webClimberSlotExtractor: WebClimberSlotExtractor
) : WebClimberClient {
    private val baseUrl = "https://%d.webclimber.de/de/booking/offer/%s"
    override fun getAllAvailableSlotsFor(date: OffsetDateTime, id: Int, specialPath: String): Collection<SlotInfo> {
        return httpClient.get(
            url = baseUrl.format(
                id,
                specialPath
            ),
            params = mapOf(
                "type" to "getTimes",
                "date" to date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "places" to "1",
                "persons" to "1",
                "period" to "2"
            ),
            headers = mapOf(
                "User-Agent" to
                        "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:95.0) Gecko/20100101 Firefox/95.0",
                "X-Requested-With" to
                        "XMLHttpRequest",
            )
        ).let { response ->
            Jsoup.parse(String(response.content, Charsets.UTF_8)).select("tr")
                .mapNotNull {
                    webClimberSlotExtractor.getSlot(it)
                }
        }
    }
}

// https://168.webclimber.de/de/booking/offer/bouldern-urban-sports-club?type=getTimes&date=2022-01-09&period=2&place_id=&places=1&persons=1