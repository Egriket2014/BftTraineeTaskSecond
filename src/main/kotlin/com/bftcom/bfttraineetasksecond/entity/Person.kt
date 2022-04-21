package com.bftcom.bfttraineetasksecond.entity

import org.springframework.data.annotation.Id

class Person (

    @Id
    val id : Int,

    var name : String,

    var lastName : String
)