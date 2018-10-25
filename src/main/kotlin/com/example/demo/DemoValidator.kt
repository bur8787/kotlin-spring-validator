package com.example.demo

import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator


@Component
class DemoValidator : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return DemoRequest1::class.java.isAssignableFrom(clazz) // (2)
    }

    override fun validate(target: Any?, errors: Errors) {

        val req = target as DemoRequest1
        val first = req.name.first
        val last = req.name.last

        if (first == "Takahiro" && last == "Suzuki") {
            errors.rejectValue(
                    "name",
                    "name error"
            )
        }
    }
}