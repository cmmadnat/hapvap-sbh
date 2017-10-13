package demo.service

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id

@Entity data class AnTransaction(@Id var id: Long? = null, var detail: String)

interface AnTransactionService
@Service
class AnTransactionServiceImpl:AnTransactionService{

}