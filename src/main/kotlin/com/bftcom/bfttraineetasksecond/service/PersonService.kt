package com.bftcom.bfttraineetasksecond.service

import com.bftcom.bfttraineetasksecond.entity.Person
import com.bftcom.bfttraineetasksecond.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(
    private val personRepository: PersonRepository
) {

    fun addPerson(name: String, lastName: String): Int {
        return personRepository.save(name, lastName)
    }

    fun getAll(): List<Person> {
        return personRepository.findAll()
    }

    fun getById(id: Int): Person? {
        return personRepository.findById(id)
            ?: throw RuntimeException("GET: Person with id = $id not found")
    }

    fun getByName(name: String): List<Person> {
        return personRepository.findByName(name)
    }

    fun getByLastName(lastName: String): List<Person> {
        return personRepository.findByLastName(lastName)
    }

    fun update(id: Int, name: String?, lastName: String?) {
        val person: Person = personRepository.findById(id)
            ?: throw RuntimeException("UPDATE: Person with id = $id not found")
        personRepository.update(Person(id, name?: person.name, lastName?: person.lastName))

    }

    fun deleteById(id: Int) {
        personRepository.deleteById(id)
    }

    fun deleteAll() {
        personRepository.deleteAll()
    }
}