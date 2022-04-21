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
        try {
            return personRepository.findById(id)?: throw Exception()

        }catch (e: Exception) {
            println("Person with id = $id not found")
            return null
        }
    }

    fun getByName(name: String): List<Person> {
        return personRepository.findByName(name)
    }

    fun getByLastName(lastName: String): List<Person> {
        return personRepository.findByLastName(lastName)
    }

    fun update(id: Int, name: String, lastName: String) {
        personRepository.update(Person(id, name, lastName))
    }

    fun deleteById(id: Int) {
        personRepository.deleteById(id)
    }

    fun deleteAll() {
        personRepository.deleteAll()
    }
}