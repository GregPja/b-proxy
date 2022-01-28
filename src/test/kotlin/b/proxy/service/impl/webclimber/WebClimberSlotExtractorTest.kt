package b.proxy.service.impl.webclimber

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset


class WebClimberSlotExtractorTest {

    private val slotExtractor = WebClimberSlotExtractor()


    @Test
    fun `data is parse correctly and please forgive me for this`() {
        val slot = slotExtractor.getSlot(Jsoup.parseBodyFragment(anExample).selectFirst("tr")!!)!!

        assertThat(slot.id, `is`("bookingBtn_61f2aca0c185c"))
        assertThat(slot.freeSpots, `is`(13))
        assertThat(slot.start, `is`(OffsetDateTime.of(2022, 1, 28, 11, 0, 0, 0, ZoneOffset.UTC)))
        assertThat(slot.end, `is`(OffsetDateTime.of(2022, 1, 28, 13, 0, 0, 0, ZoneOffset.UTC)))
    }

    @Test
    fun `data is parse correctly and please forgive me for this again`() {
        val slot = slotExtractor.getSlot(Jsoup.parseBodyFragment(anotherExample2).selectFirst("tr")!!)
        assertThat(slot, IsNull())
    }

    @Test
    fun `when the table has less than 3 td null is returned`() {

    }

    private val anExample = """
        <table class="table table-striped"><tbody>
    <tr> 
 <td>11:00 - 13:00 Uhr</td> 
 <td>13 freie Pl\xc3\xa4tze</td> 
 <td> <button class="bookingBtn btn btn-success btn-small" data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1" id="bookingBtn_61f2aca0c185c" name="yt8" type="button"> Buchen </button> </td> 
</tr></tbody>
</table>"""

    private val anotherExample = """
        <table class="table table-striped"><tbody>
    <tr> 
 <td>08:33 - 20:12 Uhr</td> 
 <td>9 freie Pl\xc3\xa4tze</td> 
 <td> <button class="bookingBtn btn btn-success btn-small" data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-08&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1" id="bookingBtn_61f2aca0c185c" name="yt8" type="button"> Buchen </button> </td> 
</tr></tbody>
</table>"""
    private val anotherExample2 = """
        <table class="table table-striped"><tbody>
    <tr> 
 <td>08:33 - 20:12 Uhr</td> 
 <td> <button class="bookingBtn btn btn-success btn-small" data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-08&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1" id="bookingBtn_61f2aca0c185c" name="yt8" type="button"> Buchen </button> </td> 
</tr></tbody>
</table>"""
}