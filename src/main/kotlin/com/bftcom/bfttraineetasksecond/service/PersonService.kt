package com.bftcom.bfttraineetasksecond.service

import com.bftcom.bfttraineetasksecond.dto.PersonDto
import com.bftcom.bfttraineetasksecond.entity.Person
import com.bftcom.bfttraineetasksecond.repository.PersonRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds

@Service
class PersonService(

    private val personRepository: PersonRepository

) {

    @Value("\${listeningDir.path}")
    private lateinit var listenerDirectory: String
    private val mapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule()

    @Transactional
    fun addPerson(name: String, lastName: String): Int {
        return if (personRepository.findByNameAndLastName(name, lastName).isNotEmpty()) {
            println("ignoring Person{ $name, $lastName }")
            -1
        } else {
            personRepository.save(name, lastName)
        }
    }

    @Transactional
    fun addAll(personDtoList: List<PersonDto>): List<Int> {
        val newIdList = mutableListOf<Int>()
        for (personDto in personDtoList) {
            newIdList.add(addPerson(personDto.name, personDto.lastName))
        }
        return newIdList
    }

    fun getAll(): List<Person> {
        return personRepository.findAll()
    }

    fun getById(id: Int): Person? {
        return personRepository.findById(id)
            ?: throw IllegalArgumentException("GET: Person with id = $id not found")
    }

    fun getByName(name: String): List<Person> {
        return personRepository.findByName(name)
    }

    fun getByLastName(lastName: String): List<Person> {
        return personRepository.findByLastName(lastName)
    }

    @Transactional
    fun update(id: Int, name: String?, lastName: String?) {
        val person: Person = personRepository.findById(id)
            ?: throw IllegalArgumentException("UPDATE: Person with id = $id not found")
        personRepository.update(Person(id, name ?: person.name, lastName ?: person.lastName))

    }

    @Transactional
    fun deleteById(id: Int) {
        personRepository.deleteById(id)
    }

    @Transactional
    fun deleteAll() {
        personRepository.deleteAll()
    }

    @Async
    fun startWatch() {
        val watchService = FileSystems.getDefault().newWatchService()
        val pathToWatch = File(listenerDirectory).toPath()

        pathToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)

        while (true) {
            val watchKey = watchService.take()
            for (event in watchKey.pollEvents()) {
                val file = File(listenerDirectory + "\\${event.context()}")
                val listPersons = parseJson(file)
                //println(listPersons)
                addAll(listPersons)
                file.delete()
            }
            watchKey.reset()
        }
    }

    private fun parseJson(file: File): List<PersonDto> {
        val jsonString: String = file.readText(Charsets.UTF_8)
        return mapper.readValue(jsonString)
    }
}