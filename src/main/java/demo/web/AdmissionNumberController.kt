package demo.web

import demo.service.*
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
    fun viewAn(@PathVariable id: Long, model: Model, anTransaction: AnTransaction): String {
        val admissionNumber = anService.findOne(id)
        val map = settingService.outcome.map { SelectTwo(it, it) }
        val map2 = settingService.diagnosisAtTimeOfHospitalAdmission.map { SelectTwo(it, it) }
        val map3 = settingService.priorAntiobiotics.map { SelectTwo(it, it) }
        val map4 = settingService.hapVapDiagnosis.map { SelectTwo(it, it) }
        val map5 = settingService.hapVapPatientsCondition.map { SelectTwo(it, it) }

        model.addAttribute("admissionNumber", admissionNumber)
        model.addAttribute("outcomes", map)
        model.addAttribute("diagnosisAtTimeOfHospitalAdmission", map2)
        model.addAttribute("priorAntiobiotics", map3)
        model.addAttribute("hapVap1", map4)
        model.addAttribute("hapVap2", map5)
        model.addAttribute("id", id)
        if (admissionNumber != null) {
            val findByAn = anTransactionService.findByAn(admissionNumber.an)
            model.addAttribute("list", findByAn)
        }
        return "viewAn"
    }

    @PostMapping("{id}/update")
    fun update(@PathVariable id: Long, @Valid anTransaction: AnTransaction, bindingResult: BindingResult): String {
        val findOne = anService.findOne(id)
        if (findOne != null) {
            anTransaction.an = findOne.an
            anTransaction.id = null
            anTransactionService.save(anTransaction)
        }
        return "redirect:/an/{id}"
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
