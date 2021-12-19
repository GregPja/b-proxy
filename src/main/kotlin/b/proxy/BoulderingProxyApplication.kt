package b.proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BoulderingProxyApplication

fun main(args: Array<String>) {
    runApplication<BoulderingProxyApplication>(*args)
}