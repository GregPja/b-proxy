package b.proxy.service.user.repository

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Month

@SpringBootTest
class ConfigurationUserRepositoryTest @Autowired constructor(
    private val repo: ConfigurationUserRepository
) {
    @Test
    fun `I can be found and date is correct`() {
        val me = repo.findUser(130755048)
        assertNotNull(me)
        assertTrue(me!!.dateOfBirth.year == 1991)
        assertTrue(me.dateOfBirth.month == Month.DECEMBER)
        assertTrue(me.dateOfBirth.dayOfMonth == 21)
    }

}