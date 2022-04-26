package com.bftcom.bfttraineetasksecond.listener

import com.bftcom.bfttraineetasksecond.dto.PersonDto
import com.bftcom.bfttraineetasksecond.service.PersonService
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class JmsListener(

    private val personService: PersonService

) {

    @JmsListener(destination = "persons")
    fun receive(xmlString: String) {
        println("Receive message")

        val xmlMapper = XmlMapper()
        val personsDtoList: List<PersonDto> = xmlMapper.readValue(
            xmlString,
            xmlMapper.typeFactory.constructCollectionType(List::class.java, PersonDto::class.java))

        personService.addAll(personsDtoList)
    }
}