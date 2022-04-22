package com.bftcom.bfttraineetasksecond.service

import com.bftcom.bfttraineetasksecond.entity.Person
import com.bftcom.bfttraineetasksecond.repository.PersonRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PersonServiceTest {

    private var person: Person = Person(1, "Ivan", "Ivanov")

    private val personRepository = Mockito.mock(PersonRepository::class.java)
    private val personService: PersonService = PersonService(personRepository)

    @Test
    fun getAll() {
        Mockito.`when`(personRepository.findAll()).thenReturn(listOf(person))

        val personList = personService.getAll()

        assertEquals(1, personList.size)
        assertEquals(person, personList[0])
    }

    @Test
    fun getById() {
        Mockito.`when`(personRepository.findById(1)).thenReturn(person)

        val resultPerson = personService.getById(1)

        assertEquals(resultPerson, person)
    }

    @Test
    fun getByIdException() {
        Mockito.`when`(personRepository.findById(2))
            .thenThrow(IllegalArgumentException("GET: Person with id = 2 not found"))

        val thrown = Assertions.assertThrows(IllegalArgumentException::class.java) {
            personService.getById(2)
        }

        assertEquals("GET: Person with id = 2 not found", thrown.message)
    }

    @Test
    fun updateException() {
        Mockito.`when`(personRepository.update(Person(2, "Fedya", "Ivanov")))
            .thenThrow(IllegalArgumentException("UPDATE: Person with id = 2 not found"))

        val thrown = Assertions.assertThrows(IllegalArgumentException::class.java) {
            personService.update(2, "Fedya", "Ivanov")
        }

        assertEquals("UPDATE: Person with id = 2 not found", thrown.message)
    }
}