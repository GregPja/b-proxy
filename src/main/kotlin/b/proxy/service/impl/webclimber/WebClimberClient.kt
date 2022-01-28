package b.proxy.service.impl.webclimber

import b.proxy.BoulderUser
import b.proxy.configurations.HttpClient
import b.proxy.service.entity.SlotInfo
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.net.URLEncoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

interface WebClimberClient {
    fun getAllAvailableSlotsFor(date: OffsetDateTime, id: Int, specialPath: String): Collection<SlotInfo>
}

data class BookingSession(
    val token: String,
    val session: String
)

@Component
class WebClimberClientImpl(
    private val httpClient: HttpClient,
    private val webClimberDataExtractor: WebClimberDataExtractor
) : WebClimberClient {
    private val slotsUrl = "https://%d.webclimber.de/de/booking/offer/%s"
    private val bookingUrl = "https://%d.webclimber.de/de/booking/book/%s"

    override fun getAllAvailableSlotsFor(date: OffsetDateTime, id: Int, specialPath: String): Collection<SlotInfo> {
        return httpClient.get(
            url = slotsUrl.format(
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
                    webClimberDataExtractor.getSlot(it)
                }
        }
    }


    fun book(date: OffsetDateTime, specialPath: String, id: Int, user: BoulderUser) {
        val firstBookingResponse = httpClient.get(
            url = bookingUrl.format(
                id,
                specialPath
            ),
            params = mapOf(
                "date" to date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "time" to date.format(DateTimeFormatter.ofPattern("hh:mm")),
                "period" to "2",
                "persons" to "1",
                "place_id" to "9"
            )
        )
        val parsedContent = Jsoup.parse(String(firstBookingResponse.content, Charsets.UTF_8))
        val tokenData = webClimberDataExtractor.getToken(parsedContent)
        val newBookingUrl = "https://$id.webclimber.de/${webClimberDataExtractor.getNewBookingUrl(parsedContent)}"

        val formData = URLEncoder.encode(getFormData(tokenData, user), Charsets.UTF_8)
    }

    private fun getFormData(tokenData: BookingSession, user: BoulderUser): String {
        return """webclimber_session=${tokenData.session}&YII_CSRF_TOKEN=${tokenData.token}&BookingOrder[0][vorname]=${user.firstName}&BookingOrder[0][nachname]=${user.lastName}&BookingOrder[0][strasse]=${user.streetName}&BookingOrder[0][plz]=${user.postalCode}&BookingOrder[0][ort]=${user.city}&BookingOrder[0][email]=${user.email}&BookingOrder[0][usc_cardnumber]=${user.urbanSportClubId}&BookingOrder[0][artikel_id]=95&BookingOrder[0][teilnehmer]=1&BookingOrder[1][vorname]=&BookingOrder[1][nachname]=&BookingOrder[1][strasse]=&BookingOrder[1][plz]=&BookingOrder[1][ort]=&BookingOrder[1][customer_no]=&BookingOrder[1][usc_cardnumber]=&BookingOrder[1][artikel_id]=95&BookingOrder[2][vorname]=&BookingOrder[2][nachname]=&BookingOrder[2][strasse]=&BookingOrder[2][plz]=&BookingOrder[2][ort]=&BookingOrder[2][customer_no]=&BookingOrder[2][usc_cardnumber]=&BookingOrder[2][artikel_id]=95&BookingOrder[3][vorname]=&BookingOrder[3][nachname]=&BookingOrder[3][strasse]=&BookingOrder[3][plz]=&BookingOrder[3][ort]=&BookingOrder[3][customer_no]=&BookingOrder[3][usc_cardnumber]=&BookingOrder[3][artikel_id]=95&ajax=booking-order-form&yt0="""
    }
}
val payments = """curl 'https://168.webclimber.de/de/booking/checkoutPayments/FHTmz8oBOGXkW#checkoutPayments' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0) Gecko/20100101 Firefox/96.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate, br' -H 'Referer: https://168.webclimber.de/de/booking/checkoutPurchase/FHTmz8oBOGXkW?alias=bouldern-urban-sports-club' -H 'Connection: keep-alive' -H 'Cookie: webclimber_session=b6a554dba734b93d0e6cb0ebdf6ab028; language=c4a2302e76e09945b746fdfe2ece2bfbb21cba92s%3A2%3A%22de%22%3B' -H 'Upgrade-Insecure-Requests: 1' -H 'Sec-Fetch-Dest: document' -H 'Sec-Fetch-Mode: navigate' -H 'Sec-Fetch-Site: same-origin' -H 'Sec-Fetch-User: ?1'"""


val confirm = """curl 'https://168.webclimber.de/de/booking/checkoutConfirm/FHTmz8oBOGXkW#checkoutConfirm' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0) Gecko/20100101 Firefox/96.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate, br' -H 'Referer: https://168.webclimber.de/de/booking/checkoutPurchase/FHTmz8oBOGXkW?alias=bouldern-urban-sports-club' -H 'Connection: keep-alive' -H 'Cookie: webclimber_session=b6a554dba734b93d0e6cb0ebdf6ab028; language=c4a2302e76e09945b746fdfe2ece2bfbb21cba92s%3A2%3A%22de%22%3B' -H 'Upgrade-Insecure-Requests: 1' -H 'Sec-Fetch-Dest: document' -H 'Sec-Fetch-Mode: navigate' -H 'Sec-Fetch-Site: same-origin' -H 'Sec-Fetch-User: ?1'"""


val ok = """curl 'https://168.webclimber.de/de/booking/checkoutConfirm/FHTmz8oBOGXkW' -X POST -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:96.0) Gecko/20100101 Firefox/96.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/x-www-form-urlencoded' -H 'Origin: https://168.webclimber.de' -H 'Connection: keep-alive' -H 'Referer: https://168.webclimber.de/de/booking/checkoutConfirm/FHTmz8oBOGXkW' -H 'Cookie: webclimber_session=b6a554dba734b93d0e6cb0ebdf6ab028; language=c4a2302e76e09945b746fdfe2ece2bfbb21cba92s%3A2%3A%22de%22%3B' -H 'Upgrade-Insecure-Requests: 1' -H 'Sec-Fetch-Dest: document' -H 'Sec-Fetch-Mode: navigate' -H 'Sec-Fetch-Site: same-origin' -H 'Sec-Fetch-User: ?1' --data-raw 'webclimber_session=b6a554dba734b93d0e6cb0ebdf6ab028&YII_CSRF_TOKEN=42aabcb39eb00cc63c5301b5ed0211d508bff626&BookingOrder%5BcheckAgb%5D=0&BookingOrder%5BcheckAgb%5D=1&yt0='"""