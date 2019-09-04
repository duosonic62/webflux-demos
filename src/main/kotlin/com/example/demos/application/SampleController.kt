package com.example.demos.application

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {
    @GetMapping("/timeout")
    fun timeout(): HttpStatus {
        Thread.sleep(10000)
        return HttpStatus.OK
    }
}
