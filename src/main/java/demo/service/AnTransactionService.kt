package demo.service

import com.googlecode.objectify.ObjectifyService.ofy
import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index
import org.springframework.stereotype.Service
import java.util.*

@Entity data class AnTransaction(@Id var id: Long? = null, @Index var an: String = "", var detail: String, var date: Date = Date())

interface AnTransactionService {
    fun findByAn(an: String): List<AnTransaction>
}

@Service
class AnTransactionServiceImpl : AnTransactionService {
    override fun findByAn(an: String): List<AnTransaction> {
        return ofy().load().type(AnTransaction::class.java).filter("an", an).order("date").list()
    }

}