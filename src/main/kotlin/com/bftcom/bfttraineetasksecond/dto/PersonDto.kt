package com.bftcom.bfttraineetasksecond.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "person")
data class PersonDto(

    @JacksonXmlProperty(localName = "name")
    var name : String,

    @JacksonXmlProperty(localName = "last-name")
    var lastName : String

)
