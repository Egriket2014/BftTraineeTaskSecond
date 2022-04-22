package com.bftcom.bfttraineetasksecond.repository

import com.bftcom.bfttraineetasksecond.entity.Person
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PersonRepositoryTest {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Test
    fun findById() {
        val id = personRepository.save("Ivan", "Ivanov")

        val person = personRepository.findById(id)

        assertEquals(id, person?.id)
        assertEquals("Ivan", person?.name)
        assertEquals("Ivanov", person?.lastName)
    }

    @Test
    fun findAll() {
        personRepository.deleteAll()
        personRepository.save("Ivan", "Ivanov")
        personRepository.save("Fedya", "Fedin")

        val result = personRepository.findAll()

        assertEquals(2, result.size)

        assertEquals("Ivan", result[0].name)
        assertEquals("Ivanov", result[0].lastName)

        assertEquals("Fedya", result[1].name)
        assertEquals("Fedin", result[1].lastName)
    }

    @Test
    fun findByName() {
        personRepository.deleteAll()
        val id = personRepository.save("Ivan", "Ivanov")

        val person = personRepository.findByName("Ivan").get(0)

        assertEquals(id, person.id)
        assertEquals("Ivan", person.name)
        assertEquals("Ivanov", person.lastName)
    }

    @Test
    fun findByLastName() {
        personRepository.deleteAll()
        val id = personRepository.save("Ivan", "Ivanov")

        val person = personRepository.findByLastName("Ivanov").get(0)

        assertEquals(id, person.id)
        assertEquals("Ivan", person.name)
        assertEquals("Ivanov", person.lastName)
    }

    @Test
    fun update() {
        val id = personRepository.save("Ivan", "Ivanov")

        personRepository.update(Person(id, "Fedya", "Ivanov"))
        val person =  personRepository.findById(id)

        assertEquals(id, person?.id)
        assertEquals("Fedya", person?.name)
        assertEquals("Ivanov", person?.lastName)
    }

    @Test
    fun save() {
        val id = personRepository.save("Ivan", "Ivanov")
        val person =  personRepository.findById(id)

        assertEquals(id, person?.id)
        assertEquals("Ivan", person?.name)
        assertEquals("Ivanov", person?.lastName)
    }

    @Test
    fun deleteById() {
        val id = personRepository.save("Ivan", "Ivanov")

        personRepository.deleteById(id)

        assertNull(personRepository.findById(id))
    }
}