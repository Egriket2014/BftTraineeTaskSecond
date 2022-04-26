package com.bftcom.bfttraineetasksecond.config

import com.bftcom.bfttraineetasksecond.listener.JsonListener
import com.bftcom.bfttraineetasksecond.service.PersonService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.devtools.filewatch.FileSystemWatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.time.Duration
import javax.annotation.PreDestroy


@Configuration
class FileWatcherConfig(
    private val personService: PersonService
) {

    @Value("\${listeningDir.path}")
    private lateinit var listenerDirectory: String

    @Bean
    fun fileSystemWatcher(): FileSystemWatcher {
        val fileSystemWatcher = FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L))
        fileSystemWatcher.addSourceDirectory(File(listenerDirectory))
        fileSystemWatcher.addListener(JsonListener(personService))
        fileSystemWatcher.start()
        println("START FILE WATCHER Directory: $listenerDirectory")
        return fileSystemWatcher
    }

    @PreDestroy
    fun destroyFun() {
        fileSystemWatcher().stop()
    }
}