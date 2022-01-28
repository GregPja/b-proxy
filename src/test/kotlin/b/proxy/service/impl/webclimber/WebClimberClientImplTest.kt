package b.proxy.service.impl.webclimber

import b.proxy.configurations.HttpClient
import khttp.responses.Response
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.time.OffsetDateTime

@SpringBootTest
@ActiveProfiles(value = ["local"])
class WebClimberClientImplTest @Autowired constructor(
    private val webClimberClientImpl: WebClimberClientImpl
) {

    @MockBean
    lateinit var httpClient: HttpClient
    @Captor
    lateinit var urlCaptor: ArgumentCaptor<String>

    @Test
    fun `when something is returned it gets mapped properly`() {
        doAnswer {
            mock<Response>().apply {
                `when`(content).thenAnswer { html.toByteArray() }
            }
        }.`when`(httpClient).get(any(), any(), any())

        val slots = webClimberClientImpl.getAllAvailableSlotsFor(OffsetDateTime.now(), 123, "yoooo")
        assertThat(slots.size, `is`(28))
    }


    @Test
    fun `when is called url is correct`(){
        doAnswer {
            mock<Response>().apply {
                `when`(content).thenAnswer { html.toByteArray() }
            }
        }.`when`(httpClient).get(urlCaptor.capture() ?: "", any(), any())

        webClimberClientImpl.getAllAvailableSlotsFor(OffsetDateTime.now(), 123, "yoooo")

        assertThat(urlCaptor.value, `is`("https://123.webclimber.de/de/booking/offer/yoooo"))
    }

    private val html = """
        b'\n
        <table class="table table-striped">\n
            <tbody>\n\n\t
            <tr>
                <td>09:00 - 11:00 Uhr</td>
                <td>14 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=09%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0bfe8e" name="yt0" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>09:15 - 11:15 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=09%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c0b80" name="yt1" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>09:30 - 11:30 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=09%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c0d8e" name="yt2" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>09:45 - 11:45 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=09%3A45&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c0f69" name="yt3" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>10:00 - 12:00 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=10%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c1133" name="yt4" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>10:15 - 12:15 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=10%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c12fc" name="yt5" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>10:30 - 12:30 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=10%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c14bb" name="yt6" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>10:45 - 12:45 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=10%3A45&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c167f" name="yt7" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>11:00 - 13:00 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c185c" name="yt8" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>11:15 - 13:15 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=11%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c1a20" name="yt9" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>11:30 - 13:30 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=11%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c1bda" name="yt10" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>11:45 - 13:45 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=11%3A45&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c1d97" name="yt11" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>12:00 - 14:00 Uhr</td>
                <td>13 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=12%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c1f51" name="yt12" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>12:15 - 14:15 Uhr</td>
                <td>14 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=12%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c210a" name="yt13" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>12:30 - 14:30 Uhr</td>
                <td>17 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=12%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c22c5" name="yt14" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>12:45 - 14:45 Uhr</td>
                <td>17 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=12%3A45&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c247b" name="yt15" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>13:00 - 15:00 Uhr</td>
                <td>17 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=13%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c2635" name="yt16" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>13:15 - 15:15 Uhr</td>
                <td>17 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=13%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c27f4" name="yt17" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>13:30 - 15:30 Uhr</td>
                <td>17 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=13%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c29b4" name="yt18" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>13:45 - 15:45 Uhr</td>
                <td>15 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=13%3A45&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c2b6b" name="yt19" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>14:00 - 16:00 Uhr</td>
                <td>15 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=14%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c2d24" name="yt20" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>14:15 - 16:15 Uhr</td>
                <td>11 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=14%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c2ed6" name="yt21" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>14:30 - 16:30 Uhr</td>
                <td>9 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=14%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c308c" name="yt22" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>14:45 - 16:45 Uhr</td>
                <td>4 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=14%3A45&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c323d" name="yt23" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>15:00 - 17:00 Uhr</td>
                <td>4 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=15%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c33f3" name="yt24" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>15:15 - 17:15 Uhr</td>
                <td>2 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=15%3A15&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c35ac" name="yt25" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>15:30 - 17:30 Uhr</td>
                <td>2 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=15%3A30&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c3765" name="yt26" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            <tr>
                <td>21:00 - 23:00 Uhr</td>
                <td>4 freie Pl\xc3\xa4tze</td>
                <td>
                    <button class="bookingBtn btn btn-success btn-small"
                            data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=21%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1"
                            id="bookingBtn_61f2aca0c391c" name="yt27" type="button">
                        Buchen
                    </button>
                </td>
            </tr>
            </tbody>
            \n
        </table>
        <script type="text/javascript"
                src="/js/jquery.plugin.min.js?v=20170311"></script>\n
        <script type="text/javascript"
                src="/js/jquery.countdown.min.js?v=20170311"></script>\n
        <script type="text/javascript">\n/*<![CDATA[*/\n\n        \n
        ${'$'}(\'.bookingBtn\').on(\'click\',function(){\n            App.showLoader();\n            ${'$'}(\'.bookingBtn\').addClass(\'disabled\').attr(\'disabled\', true);            \n            location.href=${'$'}(this).data(\'url\');                  \n        });\n    \n    \n/*]]>*/\n</script>\n'

    """.trimIndent()
}