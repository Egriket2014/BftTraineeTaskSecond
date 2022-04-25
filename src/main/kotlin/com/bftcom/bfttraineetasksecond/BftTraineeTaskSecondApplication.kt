package com.bftcom.bfttraineetasksecond

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class BftTraineeTaskSecondApplication

fun main(args: Array<String>) {
    runApplication<BftTraineeTaskSecondApplication>(*args)
}
