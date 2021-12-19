package b.proxy.configurations

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class ObjectMapperConfiguration(private val objectMapper: ObjectMapper) {
    @PostConstruct
    fun setUpObjectMapper() {
        // need to understand what is the difference between this injected by default and what I create from scratch
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
    }
}