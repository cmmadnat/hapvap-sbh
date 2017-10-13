package demo.web

import demo.service.AdmissionNumber
import demo.service.AnService
import demo.service.AnTransactionService
import demo.service.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@RequestMapping("an")
class AdmissionNumberController {
    @RequestMapping("{id}")
    fun viewAn(@PathVariable id: Long, model: Model): String {
        val admissionNumber = anService.findOne(id)
        val map = settingService.outcome.map { SelectTwo(it, it) }
        val map2 = settingService.diagnosisAtTimeOfHospitalAdmission.map { SelectTwo(it, it) }
        val map3 = settingService.priorAntiobiotics.map { SelectTwo(it, it) }

        model.addAttribute("admissionNumber", admissionNumber)
        model.addAttribute("outcomes", map)
        model.addAttribute("diagnosisAtTimeOfHospitalAdmission", map2)
        model.addAttribute("priorAntiobiotics", map3)
        model.addAttribute("id", id)
        if (admissionNumber != null) {
            model.addAttribute("list", anTransactionService.findByAn(admissionNumber.an))
        }
        return "viewAn"
    }

    @PostMapping("{id}/updateAuxData")
    fun updateAuxData(@Valid admissionNumber: AdmissionNumber, bindingResult: BindingResult, @PathVariable id: Long): String {
        val findOne = anService.findOne(id)
        if (findOne != null) {
            findOne.outcome = admissionNumber.outcome
            findOne.priorAntiobiotics = admissionNumber.priorAntiobiotics
            findOne.priorAntiobioticsOther = admissionNumber.priorAntiobioticsOther
            findOne.diagnosis = admissionNumber.diagnosis
            findOne.diagnosisOther = admissionNumber.diagnosisOther
            anService.update(id, findOne)
        }
        return "redirect:/an/$id"
    }

    @Autowired lateinit var anService: AnService
    @Autowired lateinit var anTransactionService: AnTransactionService
    @Autowired lateinit var settingService: SettingService

}

data class SelectTwo(var id: String = "", var name: String = "")
