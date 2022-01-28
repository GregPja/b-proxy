package b.proxy.configurations

import b.proxy.service.BoulderingService
import b.proxy.service.impl.drplano.DrPlanoService
import b.proxy.service.impl.drplano.client.DrPlanoClient
import b.proxy.service.impl.drplano.client.DrPlanoClientImpl
import b.proxy.service.impl.webclimber.WebClimberClient
import b.proxy.service.impl.webclimber.WebClimberService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BoulderingServiceConfiguration(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper,
    private val boulderingConfiguration: ServicesConfigurations,
    private val webClimberClient: WebClimberClient
) {
    @Bean
    fun getDrPlanoClient(): DrPlanoClient {
        return DrPlanoClientImpl(
            httpClient = httpClient,
            objectMapper = objectMapper
        )
    }

    @Bean
    fun getAllDrPlanoService(): List<BoulderingService> {
        return boulderingConfiguration.drPlano.filter { it.active }
            .map {
                DrPlanoService(
                    name = it.name,
                    drPlanoClient = getDrPlanoClient(),
                    clientId = it.clientId,
                    shiftModelId = it.shiftModelId,
                    urbanSportClubTariffId = it.urbanSportClubTariffId,
                    origin = it.origin
                )
            } + boulderingConfiguration.webClimber.filter { it.active }
            .map {
                WebClimberService(
                    name = it.name,
                    webClimberClient = webClimberClient,
                    webClimberId = it.webClimberId,
                    specialPath = it.specialPath
                )
            }
    }

}


@ConstructorBinding
@ConfigurationProperties("configuration")
data class ServicesConfigurations(
    val drPlano: List<DrPlanoServiceConfiguration>,
    val webClimber: List<WebClimberConfiguration>
)

data class DrPlanoServiceConfiguration(
    val name: String,
    val clientId: Int,
    val shiftModelId: Int,
    val urbanSportClubTariffId: Int,
    val origin: String,
    val active: Boolean
)


data class WebClimberConfiguration(
    val name: String,
    val webClimberId: Int,
    val specialPath: String,
    val active: Boolean
)

