package demo

import demo.service.*
import org.joda.time.DateTime
import org.joda.time.chrono.BuddhistChronology
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.format.Formatter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*
import javax.validation.Valid

@Configuration
@EnableAutoConfiguration
open class Application {
    @Bean open fun hnService(): HnService = HnServiceImpl()
    @Bean open fun anService(): AnService = AnServiceImpl()
    @Bean open fun homeController(): HomeController = HomeController()
    @Bean open fun hospitalNumberController(): HospitalNumberController = HospitalNumberController()
    @Bean
    open fun multipartResolver(): MultipartResolver = CommonsMultipartResolver()

    @Bean open fun dateFormat(): CustomDateFormat = CustomDateFormat()

    @Bean
    open fun messageSource(): ReloadableResourceBundleMessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setBasename("classpath:lang/messages")
        messageSource.setCacheSeconds(10) //reload messages every 10 seconds
        return messageSource
    }

    @Bean
    open fun localeResolver(): LocaleResolver {
        val slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.forLanguageTag("th"))
        return slr
    }

    @Bean
    open fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }
}

class CustomDateFormat : Formatter<Date> {
    val dateFormat: DateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy").withChronology(BuddhistChronology.getInstance())
    override fun print(p0: Date?, p1: Locale?): String {
        return DateTime(p0).toString(dateFormat)
    }

    override fun parse(p0: String?, p1: Locale?): Date {
        return DateTime.parse(p0, dateFormat).toDate()
    }

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
    fun hospitalNumber(webDataBinder: WebDataBinder) {
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
        val newHospitalNumber = hnService.newHospitalNumber(hospitalNumber)
        return "redirect:/hn/$newHospitalNumber"
    }


    @Autowired lateinit var hnService: HnService
}

@Controller
@RequestMapping("hn")
class HospitalNumberController {
    @RequestMapping("/{id}")
    fun view(@PathVariable id: Long, model: Model, admissionNumber: AdmissionNumber): String {
        val findOne = hnService.findOne(id)
        if (findOne != null) {
            model.addAttribute("hn", findOne.hn)
            model.addAttribute("id", id)
            model.addAttribute("list", anService.findAnByHn(findOne.hn))
        }
        return "viewAnList"
    }

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.addValidators(AdmissionNumberValidator())
    }

    class AdmissionNumberValidator : Validator {
        override fun validate(p0: Any?, p1: Errors?) {
            ValidationUtils.rejectIfEmpty(p1, "an", "required")
        }

        override fun supports(p0: Class<*>): Boolean {
            return p0.isAssignableFrom(AdmissionNumber::class.java)
        }

    }

    @PostMapping("{id}/new")
    fun newAn(@Valid admissionNumber: AdmissionNumber, bindingResult: BindingResult, @PathVariable id: Long, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id)
            return "anListNew"
        }

        val findOne = hnService.findOne(id)
        if (findOne != null) {
            admissionNumber.hn = findOne.hn
            val newAdmissionNumber = anService.newAdmissionNumber(admissionNumber)
            return "redirect:/an/$newAdmissionNumber"
        }
        return "redirect:/hn/$id"
    }

    @RequestMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        hnService.delete(id)
        return "redirect:/"
    }

    @Autowired lateinit var hnService: HnService
    @Autowired lateinit var anService: AnService
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
