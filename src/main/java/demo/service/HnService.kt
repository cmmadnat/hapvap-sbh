package demo.service

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.ObjectifyService.ofy
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import java.util.*

@Entity data class HospitalNumber(@Id var id: Long? = null, var name: String = "", @Index var hn: String = "",
                                  @Index var date: Date = Date(), @Index var appUser:Long?=null)

interface HnService {
    fun findAll(page: Int, limit: Int): MutableList<HospitalNumber>
    fun newHospitalNumber(hospitalNumber: HospitalNumber): Long
}

@Service
class HnServiceImpl : HnService, CommandLineRunner {
    override fun newHospitalNumber(hospitalNumber: HospitalNumber): Long {
        return ofy().save().entity(hospitalNumber).now().id
    }

    override fun findAll(page: Int, limit: Int): MutableList<HospitalNumber> {
        return ofy().load().type(HospitalNumber::class.java).limit(limit).order("-date").offset(page * limit).list()
    }

    override fun run(vararg p0: String?) {
        ObjectifyService.register(HospitalNumber::class.java)
    }

}
