package demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Configuration
@EnableAutoConfiguration
open class Application {
    @Bean open fun homeController(): HomeController = HomeController()
}

@Controller
@RequestMapping("/")
class HomeController() {
    @RequestMapping
    fun index(): String {
        return "hnList"
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
