package com.bftcom.bfttraineetasksecond.config

import com.bftcom.bfttraineetasksecond.dto.PersonDto
import com.bftcom.bfttraineetasksecond.service.PersonService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.devtools.filewatch.ChangedFile
import org.springframework.boot.devtools.filewatch.ChangedFiles
import org.springframework.boot.devtools.filewatch.FileChangeListener
import org.springframework.stereotype.Component
import java.io.File

@Component
class JsonListener(

    private val personService: PersonService

    ) : FileChangeListener {

    private val mapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule()

    override fun onChange(changeSet: MutableSet<ChangedFiles>) {
        for (files: ChangedFiles in changeSet) {
            for (file: ChangedFile in files.files) {
                if (file.type.equals(ChangedFile.Type.ADD)) {
                    val listPersonsDto = parseJson(file.file)
                    println(listPersonsDto)
                    personService.addAll(listPersonsDto)
                    file.file.delete()
                }
            }
        }
    }

    private fun parseJson(file: File): List<PersonDto> {
        val jsonString: String = file.readText(Charsets.UTF_8)
        return mapper.readValue(jsonString)
    }
}