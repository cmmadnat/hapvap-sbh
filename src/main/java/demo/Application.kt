package demo

import demo.service.*
import demo.web.AdmissionNumberController
import demo.web.HomeController
import demo.web.HospitalNumberController
import org.joda.time.DateTime
import org.joda.time.chrono.BuddhistChronology
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.format.Formatter
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

@Configuration
@EnableAutoConfiguration
open class Application {
    @Bean open fun hnService(): HnService = HnServiceImpl()
    @Bean open fun anService(): AnService = AnServiceImpl()
    @Bean open fun anTransactionService(): AnTransactionService = AnTransactionServiceImpl()
    @Bean open fun homeController(): HomeController = HomeController()
    @Bean open fun hospitalNumberController(): HospitalNumberController = HospitalNumberController()
    @Bean open fun admissionNumberController():AdmissionNumberController = AdmissionNumberController()
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

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
