package com.example.demo

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Size


@RestController
class DemoController(
        val demoValidator: DemoValidator
) {
    /**
     * post1にのみ適用するバリデーターの設定
     *
     * 引数にはリクエストクラスの先頭小文字のキャメルケースを指定
     */
    @InitBinder("demoRequest1")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(demoValidator)
    }

    @GetMapping("/")
    fun get() = DemoResponse()

    /**
     * 相関チェックしたい
     */
    @PostMapping("/post1")
    fun post1(
            @RequestBody
            @Validated
            req: DemoRequest1
    ) = DemoResponse(
            message = req.message,
            name = req.name
    )

    /**
     * 相関チェックしたくない
     */
    @PostMapping("/post2")
    fun post2(
            @RequestBody @Validated req: DemoRequest2
    ) = DemoResponse(
            message = req.message,
            name = req.name
    )
}

data class Name(
        @field:Size(min = 2)
        val first: String,
        @field:Size(min = 2)
        val last: String
)

data class DemoRequest1(
        val message: String,
        @field:Valid
        val name: Name
)

data class DemoRequest2(
        val message: String,
        @field:Valid
        val name: Name
)

data class DemoResponse(
        val message: String = "Hello",
        val name: Name = Name(
                first = "Takahiro",
                last = "Suzuki"
        )
)

