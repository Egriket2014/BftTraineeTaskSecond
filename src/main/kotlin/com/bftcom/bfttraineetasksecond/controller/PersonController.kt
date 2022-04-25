package com.bftcom.bfttraineetasksecond.controller

import com.bftcom.bfttraineetasksecond.entity.Person
import com.bftcom.bfttraineetasksecond.service.PersonService
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RestController
@RequestMapping("/person")
class PersonController(

    private val personService: PersonService
) {

    @PostConstruct
    fun start() {
        personService.startWatch()
    }

    @PostMapping("/add")
    fun add(@RequestParam name: String,
            @RequestParam lastName: String): Int {

    return personService.addPerson(name, lastName)
    }

    @PutMapping("/update/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestParam name: String?,
               @RequestParam lastName: String?) {

        personService.update(id, name, lastName)
    }

    @GetMapping("/name/{name}")
    fun getByName(@PathVariable("name") name: String): List<Person> {
        return personService.getByName(name)
    }

    @GetMapping("/lastName/{lastName}")
    fun getByLastname(@PathVariable("lastName") lastname: String): List<Person> {
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

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: Int) {
        personService.deleteById(id)
    }

    @DeleteMapping("/delete/all")
    fun deleteAll() {
        personService.deleteAll()
    }
}