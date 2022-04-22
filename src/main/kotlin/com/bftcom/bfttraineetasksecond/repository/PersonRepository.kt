package com.bftcom.bfttraineetasksecond.repository

import com.bftcom.bfttraineetasksecond.entity.Person
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class PersonRepository (
    private val jdbcTemplate: JdbcTemplate
        ) {

    private val rowMapper : RowMapper<Person> = RowMapper<Person> { rs: ResultSet, _: Int ->
        Person(rs.getInt("id"), rs.getString("name"), rs.getString("lastName")) }


    fun findAll(): List<Person> {
        return jdbcTemplate.query("select * from Person", rowMapper)
    }

    fun findById(id: Int): Person? {
        return jdbcTemplate.query("select * from Person where id=?", rowMapper, id)
            .firstOrNull()
    }

    fun findByName(name: String): List<Person> {
        return jdbcTemplate.query("select * from Person where name=?", rowMapper, name)
    }

    fun findByLastName(lastName: String): List<Person> {
        return jdbcTemplate.query("select * from Person where lastName=?", rowMapper, lastName)
    }

    fun update(entity: Person) {
        jdbcTemplate.update("update person set name=?, lastName=? where id=?", entity.name, entity.lastName, entity.id)
    }

    fun save(name: String, lastName: String): Int {
        val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)
            .withTableName("person")
            .usingGeneratedKeyColumns("id")

        val parameters: MutableMap<String, String> = HashMap()
        parameters.put("name", name)
        parameters.put("lastName", lastName)
        return simpleJdbcInsert.executeAndReturnKey(parameters) as Int
    }

    fun deleteById(id: Int) {
        jdbcTemplate.update("delete from person where id=?", id)
    }

    fun deleteAll() {
        jdbcTemplate.update("delete from person")
    }
}