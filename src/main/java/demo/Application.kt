package demo

import demo.service.HnService
import demo.service.HnServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Configuration
@EnableAutoConfiguration
open class Application {
    @Bean open fun hnService(): HnService = HnServiceImpl()
    @Bean open fun homeController(): HomeController = HomeController()
}

@Controller
@RequestMapping("/")
class HomeController {
    @RequestMapping
    fun index(model: Model): String {
        val list = hnService.findAll(0, 200)
        model.addAttribute("list", list)
        return "hnList"
    }

    @Autowired lateinit var hnService: HnService
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
