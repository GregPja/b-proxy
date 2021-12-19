package b.proxy.service.user

import b.proxy.BoulderUser
import b.proxy.service.user.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

interface UserService {
    fun exists(userId: Long): Boolean
    fun getUser(userId: Long): BoulderUser
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun exists(userId: Long): Boolean {
        return userRepository.findUser(userId) != null
    }

    override fun getUser(userId: Long): BoulderUser {
        return userRepository.findUser(userId) ?: throw UserNotFoundException(userId)
    }

}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UserNotFoundException(userId: Long) : RuntimeException("User with id $userId not found")