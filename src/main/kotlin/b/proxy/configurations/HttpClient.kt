package b.proxy.configurations

import khttp.responses.Response
import org.springframework.stereotype.Component

@Component
class HttpClient {
    fun get(
        url: String,
        params: Map<String, Any> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Response {
        return khttp.get(
            url = url,
            params = params.mapValues { it.value.toString() },
            headers = headers,
        ).also { println("GET -> ${it.url}") }
    }

    fun post(
        url: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, Any> = emptyMap()
    ): Response {
        return khttp.post(
            url = url,
            json = body,
            headers = headers + ("Content-Type" to "application/json"),
            params = params.mapValues { it.value.toString() }
        ).also { println("POST -> ${it.url}") }
    }

    fun option(
        url: String,
        params: Map<String, Any> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Response {
        return khttp.options(
            url = url,
            params = params.mapValues { it.value.toString() },
            headers = headers
        ).also { println("OPTION -> ${it.url}") }
    }


}