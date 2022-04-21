package com.bftcom.bfttraineetasksecond.controller

import com.bftcom.bfttraineetasksecond.entity.Person
import com.bftcom.bfttraineetasksecond.service.PersonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(

    private val personService: PersonService
) {

    @GetMapping("/add")
    fun add(@RequestParam name: String,
            @RequestParam lastName: String): Int {

    return personService.addPerson(name, lastName)
    }

    @GetMapping("/update/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestParam name: String,
               @RequestParam lastName: String) {

        personService.update(id, name, lastName)
    }

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable("name") name: String): List<Person> {
        return personService.getByName(name)
    }

    @GetMapping("/lastname/{lastname}")
    fun getByLastname(@PathVariable("lastname") lastname: String): List<Person> {
        return personService.getByLastName(lastname)
    }

    @GetMapping("/id/{id}")
    fun getById(@PathVariable("id") id: Int) : Person? {
        return personService.getById(id)
    }

    @GetMapping("/all")
    fun getAll(): List<Person> {
        return personService.getAll()
    }

    @GetMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: Int) {
        personService.deleteById(id)
    }

    @GetMapping("/delete/all")
    fun deleteAll() {
        personService.deleteAll()
    }
}