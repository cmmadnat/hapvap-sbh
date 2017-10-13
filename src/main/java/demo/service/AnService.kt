package demo.service

import com.googlecode.objectify.ObjectifyService.ofy
import com.googlecode.objectify.ObjectifyService.register
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import java.util.*

@Entity data class AdmissionNumber(@Id var id: Long? = null, var name: String = "", @Index var an: String = "",
                                   @Index var admissionDate: Date? = Date(),
                                   @Index
                                   var hn: String = "",
                                   var diagnosis: MutableList<String> = mutableListOf(),
                                   var priorAntiobiotics: MutableList<String> = mutableListOf(),
                                   var diagnosisOther: String = "",
                                   var priorAntiobioticsOther: String = "",
                                   var outcome: String = "",
                                   @Index
                                   var date: Date = Date(), @Index var appUser: Long? = null)

interface AnService {
    fun findAll(page: Int, limit: Int): MutableList<AdmissionNumber>
    fun newAdmissionNumber(AdmissionNumber: AdmissionNumber): Long
    fun findAnByHn(hn: String): MutableList<AdmissionNumber>
    fun delete(id: Long)
    fun findOne(id: Long): AdmissionNumber?
    fun update(id: Long, findOne: AdmissionNumber)
}

@Service
class AnServiceImpl : AnService, CommandLineRunner {
    override fun update(id: Long, findOne: AdmissionNumber) {
        findOne.id = id
        ofy().save().entity(findOne)
    }

    override fun findAnByHn(hn: String): MutableList<AdmissionNumber> {
        return ofy().load().type(AdmissionNumber::class.java).order("date").filter("hn", hn).list()
    }

    override fun delete(id: Long) {
        val AdmissionNumber = findOne(id)

        if (AdmissionNumber != null) ofy().delete().entity(AdmissionNumber).now()
    }

    override fun findOne(id: Long): AdmissionNumber? {
        return ofy().load().type(AdmissionNumber::class.java).id(id).now()
    }

    override fun newAdmissionNumber(AdmissionNumber: AdmissionNumber): Long {
        return ofy().save().entity(AdmissionNumber).now().id
    }

    override fun findAll(page: Int, limit: Int): MutableList<AdmissionNumber> {
        return ofy().load().type(AdmissionNumber::class.java).limit(limit).order("-date").offset(page * limit).list()
    }

    override fun run(vararg p0: String?) {
        register(AdmissionNumber::class.java)
    }

}