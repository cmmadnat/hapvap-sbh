package demo.web

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("an")
class AdmissionNumberController{
    @RequestMapping("{id}")
    fun viewAn(@PathVariable id:Long, model: Model): String {
       return "viewAn"
    }

}