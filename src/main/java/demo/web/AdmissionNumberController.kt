package demo.web

import demo.service.AnService
import demo.service.AnTransactionService
import demo.service.SettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("an")
class AdmissionNumberController {
    @RequestMapping("{id}")
    fun viewAn(@PathVariable id: Long, model: Model): String {
        val admissionNumber = anService.findOne(id)
        model.addAttribute("admissionNumber", admissionNumber)
        val map = settingService.outcome.map { SelectTwo(it, it) }
        model.addAttribute("outcomes", map)
        if (admissionNumber != null) {
            model.addAttribute("list", anTransactionService.findByAn(admissionNumber.an))
        }
        return "viewAn"
    }

    @Autowired lateinit var anService: AnService
    @Autowired lateinit var anTransactionService: AnTransactionService
    @Autowired lateinit var settingService: SettingService

}

data class SelectTwo(var id: String = "", var name: String = "")
