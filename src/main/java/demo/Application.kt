package demo

import demo.service.HnService
import demo.service.HnServiceImpl
import demo.service.HospitalNumber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Configuration
@EnableAutoConfiguration
open class Application {
    @Bean open fun hnService(): HnService = HnServiceImpl()
    @Bean open fun homeController(): HomeController = HomeController()
}

@Controller
@RequestMapping("/")
class HomeController {
    @GetMapping
    fun index(model: Model, hospitalNumber: HospitalNumber): String {
        val list = hnService.findAll(0, 200)
        model.addAttribute("list", list)
        return "hnList"

    }
    @InitBinder("hospitalNumber")
    fun hospitalNumber(webDataBinder: WebDataBinder){
        webDataBinder.addValidators(HospitalNumberValidator())
    }

    class HospitalNumberValidator : Validator {
        override fun validate(p0: Any?, p1: Errors?) {
            ValidationUtils.rejectIfEmpty(p1, "hn", "required")
        }

        override fun supports(p0: Class<*>): Boolean {
            return p0.isAssignableFrom(HospitalNumber::class.java)
        }

    }

    @PostMapping("new")
    fun newHospitalNumber(@Valid hospitalNumber: HospitalNumber, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            return "hnListNew"
        }
        hnService.newHospitalNumber(hospitalNumber)
        return "redirect:/"
    }

    @Autowired lateinit var hnService: HnService
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
