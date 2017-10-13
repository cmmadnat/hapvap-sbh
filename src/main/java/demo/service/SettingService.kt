package demo.service

import org.springframework.stereotype.Service
import java.io.File

interface SettingService {
    val outcome: List<String>
}

@Service
class SettingServiceImpl : SettingService {
    override val outcome: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("outcome.txt").file
                field = File(file).readLines()
            }
            return field
        }
}