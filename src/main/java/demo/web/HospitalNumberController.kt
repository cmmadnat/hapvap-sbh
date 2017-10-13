package demo.web

import demo.service.AdmissionNumber
import demo.service.AnService
import demo.service.HnService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

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