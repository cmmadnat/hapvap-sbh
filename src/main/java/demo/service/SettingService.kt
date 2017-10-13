package demo.service

import org.springframework.stereotype.Service
import java.io.File

interface SettingService {
    val outcome: List<String>
    val underlyingDiseases: List<String>
    val diagnosisAtTimeOfHospitalAdmission: List<String>
    val priorAntiobiotics: List<String>
}

@Service
class SettingServiceImpl : SettingService {
    override val priorAntiobiotics: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("priorAntiobiotics.txt").file
                field = File(file).readLines()
            }
            return field
        }
    override val diagnosisAtTimeOfHospitalAdmission: List<String> = listOf()
        get() {

            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("diagnosisAtTimeOfHospitalAdmission.txt").file
                field = File(file).readLines()
            }
            return field
        }

    override val underlyingDiseases: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("underlyingDiseases.txt").file
                field = File(file).readLines()
            }
            return field
        }
    override val outcome: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("outcome.txt").file
                field = File(file).readLines()
            }
            return field
        }
}