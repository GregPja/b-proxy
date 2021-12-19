package b.proxy.api

import b.proxy.api.dto.SlotBookingRequestDTO
import b.proxy.api.dto.SlotInfoDTO
import b.proxy.service.BoulderingBookingService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("/book")
class BoulderingBookingController(
    private val boulderingBookingService: BoulderingBookingService
) {

    @GetMapping
    @ResponseBody
    fun doTheTest(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        from: OffsetDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        to: OffsetDateTime
    ): Map<String, Collection<SlotInfoDTO>> {
        return boulderingBookingService.getAllFreeSlots(
            from,
            to
        ).mapValues { (_, slots) -> slots.map { SlotInfoDTO(it) }.sortedBy { it.start } }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun book(
        @RequestBody
        bookingRequest: SlotBookingRequestDTO
    ) {
        println(bookingRequest)
//
//        boulderingBookingService.bookSlot(
//            bookingRequest
//        )
    }

}