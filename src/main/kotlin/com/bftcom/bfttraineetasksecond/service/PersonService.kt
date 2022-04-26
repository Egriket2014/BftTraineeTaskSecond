package com.bftcom.bfttraineetasksecond.service

import com.bftcom.bfttraineetasksecond.dto.PersonDto
import com.bftcom.bfttraineetasksecond.entity.Person
import com.bftcom.bfttraineetasksecond.repository.PersonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonService(

    private val personRepository: PersonRepository

) {

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
}
