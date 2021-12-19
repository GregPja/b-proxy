package b.proxy.service.user.repository

import b.proxy.BoulderUser
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface UserRepository {
    fun findUser(userId: Long): BoulderUser?
}

class BoulderUserConfiguration(
    firstName: String,
    lastName: String,
    email: String,
    urbanSportClubId: String,
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    dateOfBirth: LocalDate,
    streetName: String,
    city: String,
    phoneNumber: String,
    postalCode: String
) : BoulderUser(
    firstName,
    lastName,
    email,
    urbanSportClubId,
    dateOfBirth,
    streetName,
    city,
    phoneNumber,
    postalCode,
)

@ConstructorBinding
@ConfigurationProperties("users")
class UsersConfigurations(
    val map: Map<Long, BoulderUserConfiguration>
)

@Repository
class ConfigurationUserRepository(
    private val userConfigurations: UsersConfigurations
) : UserRepository {

    override fun findUser(userId: Long): BoulderUser? {
        return userConfigurations.map[userId]
    }

}