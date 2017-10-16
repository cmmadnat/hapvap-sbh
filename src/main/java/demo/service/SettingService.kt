package demo.service

import org.springframework.stereotype.Service
import java.io.File

interface SettingService {
    val outcome: List<String>
    val underlyingDiseases: List<String>
    val diagnosisAtTimeOfHospitalAdmission: List<String>
    val priorAntiobiotics: List<String>
    val hapVapDiagnosis: List<String>
    val hapVapPatientsCondition: List<String>
    val durationAntibiotics: List<String>
    val chestXray: List<String>
    val investigation: List<String>
    val sputumCulture: List<String>
}

@Service
class SettingServiceImpl : SettingService {
    override val investigation: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("investigation.txt").file
                field = File(file).readLines()
            }
            return field
        }

    override val sputumCulture: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("sputumCulture.txt").file
                field = File(file).readLines()
            }
            return field
        }
    override val durationAntibiotics: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("durationAntibiotics.txt").file
                field = File(file).readLines()
            }
            return field
        }
    override val chestXray: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("chestXray.txt").file
                field = File(file).readLines()
            }
            return field
        }

    override val hapVapDiagnosis: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("hapVapDiagnosis.txt").file
                field = File(file).readLines()
            }
            return field
        }
    override val hapVapPatientsCondition: List<String> = listOf()
        get() {
            if (field.isEmpty()) {
                val file = javaClass.classLoader.getResource("hapVapPatientsCondition.txt").file
                field = File(file).readLines()
            }
            return field
        }
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