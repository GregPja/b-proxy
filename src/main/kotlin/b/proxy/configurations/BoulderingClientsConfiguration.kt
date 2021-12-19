package b.proxy.configurations

import b.proxy.service.BoulderingService
import b.proxy.service.impl.drplano.DrPlanoService
import b.proxy.service.impl.drplano.client.DrPlanoClient
import b.proxy.service.impl.drplano.client.DrPlanoClientImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConstructorBinding
@ConfigurationProperties("configuration")
data class ServicesConfigurations(
    val drPlano: List<DrPlanoServiceConfiguration>
)

data class DrPlanoServiceConfiguration(
    val name: String,
    val clientId: Int,
    val shiftModelId: Int,
    val urbanSportClubTariffId: Int,
    val origin: String,
    val active: Boolean
)

@Configuration
class DrPlanoBoulderingBeansConfiguration(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper,
    private val drPlanoConfigurations: ServicesConfigurations
) {
    @Bean
    fun getBasementClient(): DrPlanoClient {
        return DrPlanoClientImpl(
            httpClient = httpClient,
            objectMapper = objectMapper
        )
    }

    @Bean
    fun getAllDrPlanoService(): List<BoulderingService> {
        return drPlanoConfigurations.drPlano.filter { it.active }
            .map {
                DrPlanoService(
                    name = it.name,
                    drPlanoClient = getBasementClient(),
                    clientId = it.clientId,
                    shiftModelId = it.shiftModelId,
                    urbanSportClubTariffId = it.urbanSportClubTariffId,
                    origin = it.origin
                )
            }
    }

}