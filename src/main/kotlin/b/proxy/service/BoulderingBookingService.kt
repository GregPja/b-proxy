package b.proxy.service

import b.proxy.api.dto.SlotRequest
import b.proxy.service.entity.SlotInfo
import b.proxy.service.user.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

interface BoulderingBookingService {
    fun getAllFreeSlots(from: OffsetDateTime, to: OffsetDateTime): Map<String, Collection<SlotInfo>>
    fun bookSlot(request: UserSlotBookingRequest)
}

@Service
class BoulderingBookingServiceImpl(
    boulderingServices: List<BoulderingService>,
    private val userService: UserService
) : BoulderingBookingService {
    private val serviceMap: Map<String, BoulderingService> = boulderingServices.associateBy { it.name }

    override fun getAllFreeSlots(from: OffsetDateTime, to: OffsetDateTime): Map<String, Collection<SlotInfo>> {
        return runBlocking(Dispatchers.Default) {
            serviceMap.entries.pmap { (key, value) ->
                println(value.name)
                key to value.getFreeSlots(from, to)
            }.toMap()
        }
    }

    override fun bookSlot(request: UserSlotBookingRequest) {
        val user = userService.getUser(request.userId)
        serviceMap[request.place]!!.bookSlot(user, request)
    }


}


class UserSlotBookingRequest(
    id: String,
    start: OffsetDateTime,
    end: OffsetDateTime,
    val userId: Long,
    val place: String
) : SlotRequest(
    id, start, end
)

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}