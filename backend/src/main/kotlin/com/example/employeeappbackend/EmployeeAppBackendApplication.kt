package com.example.employeeappbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class EmployeeAppBackendApplication

fun main(args: Array<String>) {
    runApplication<EmployeeAppBackendApplication>(*args)
}
