package b.proxy

import java.time.LocalDate

open class BoulderUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val urbanSportClubId: String,
    val dateOfBirth: LocalDate,
    val streetName: String,
    val city: String,
    val phoneNumber: String,
    val postalCode: String
)