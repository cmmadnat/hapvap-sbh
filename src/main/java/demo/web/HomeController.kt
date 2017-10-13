package demo.web

import demo.service.HnService
import demo.service.HospitalNumber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
@RequestMapping("/")
class HomeController {
    @RequestMapping("search")
    fun search(@RequestParam hn: String, model: Model): String {
        val hn = hnService.searchHn(hn)
        if (hn != null) {
            return "redirect:/hn/${hn.id}"
        }
        return "searchNotFound"
    }

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