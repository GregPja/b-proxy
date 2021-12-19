package b.proxy.service.impl.drplano.client.dto

import b.proxy.BoulderUser
import java.time.format.DateTimeFormatter

data class DrPlanoBookingRequest(
    val clientId: Int,
    val shiftModelId: Int,
    val shiftSelector: Any,
    val desiredDate: String? = null,
    val dateOfBirthString: String,
    val streetAndHouseNumber: String,
    val postalCode: String,
    val city: String,
    val phoneMobile: String,
    val type: String = "booking",
    val participants: List<DrPlanoParticipant>,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: String
) {

    constructor(
        user: BoulderUser,
        clientId: Int,
        shiftModelId: Int,
        shiftSelector: List<Any>,
        urbanSportClubTariffId: Int
    ) :
            this(
                clientId = clientId,
                shiftModelId = shiftModelId,
                shiftSelector = shiftSelector,
                dateOfBirthString = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(user.dateOfBirth),
                streetAndHouseNumber = user.streetName,
                postalCode = user.postalCode,
                city = user.city,
                phoneMobile = user.phoneNumber,
                participants = listOf(DrPlanoParticipant(user, urbanSportClubTariffId)),
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                dateOfBirth = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(user.dateOfBirth),
            )
}

data class DrPlanoParticipant(
    val isBookingPerson: Boolean = true,
    val tariffId: Int,
    val dateOfBirthString: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val additionalFieldValue: String,
    val dateOfBirth: String,
) {

    constructor(user: BoulderUser, urbanSportClubTariffId: Int) : this(
        tariffId = urbanSportClubTariffId,
        dateOfBirthString = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(user.dateOfBirth),
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        additionalFieldValue = user.urbanSportClubId,
        dateOfBirth = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(user.dateOfBirth),
    )
}