package demo.service

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Entity data class HospitalNumber(@Id var id: Long? = null, var name: String = "", @Index var hn: String = "")

interface HnService {
}

@Service
class HnServiceImpl : HnService, CommandLineRunner {
    override fun run(vararg p0: String?) {
        ObjectifyService.register(HospitalNumber::class.java)
    }

}
