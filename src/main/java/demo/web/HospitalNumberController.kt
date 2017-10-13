package demo.web

import com.sun.org.apache.bcel.internal.generic.Select
import demo.service.*
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
        model.addAttribute("underlyingDiseases", settingService.underlyingDiseases.map { SelectTwo(it, it) })
        if (findOne != null) {
            model.addAttribute("hospitalNumber", findOne)
            model.addAttribute("hn", findOne.hn)
            model.addAttribute("id", id)
            model.addAttribute("list", anService.findAnByHn(findOne.hn))
        }
        return "viewAnList"
    }

    @PostMapping("{id}/updateUnderlyingDiseases")
    fun updateUnderlying(@PathVariable id: Long, @Valid hospitalNumber: HospitalNumber, bindingResult: BindingResult): String {
        val findOne = hnService.findOne(id)
        if (findOne != null) {
            findOne.underlyingDisease = hospitalNumber.underlyingDisease
            findOne.underlyingDiseaseOther = hospitalNumber.underlyingDiseaseOther
            hnService.update(id, findOne)
        }
        return "redirect:/hn/$id"

    }

    @InitBinder("admissionNumber")
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.addValidators(AdmissionNumberValidator())
    }

    @InitBinder("hospitalNumber")
    fun initBinder2(webDataBinder: WebDataBinder) {
        webDataBinder.addValidators(UnderlyingDiseaseValidator())
    }

    class UnderlyingDiseaseValidator : Validator {
        override fun validate(p0: Any?, p1: Errors?) {
        }

        override fun supports(p0: Class<*>): Boolean {
            return p0.isAssignableFrom(HospitalNumber::class.java)
        }

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
    @Autowired lateinit var settingService: SettingService
}