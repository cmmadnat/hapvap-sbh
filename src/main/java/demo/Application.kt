package demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
open class Application {

    @Value("\${info.version}")
    @get:RequestMapping("/version")
    val version: String? = null

    @RequestMapping("/")
    fun home(): String {
        return "Hello World"
    }


}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
