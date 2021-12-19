package b.proxy.api

import b.proxy.BoulderUser
import b.proxy.service.user.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class BoulderingUserController(
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): BoulderUser {
        return userService.getUser(userId)
    }
}