package com.acme.tour.advice

import com.acme.tour.exception.PromotionNotFoundException
import com.acme.tour.model.PayLoadError
import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler(JsonParseException::class)
    fun jsonFormatterExceptionHandler(request: HttpServletRequest, response: HttpServletResponse, ex: Exception) =
        ResponseEntity(
                PayLoadError("Json Formatter Exception", ex.message ?: "Invalid json"),
                HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(PromotionNotFoundException::class)
    fun promotionNotFoundExceptionHandler(request: HttpServletRequest, response: HttpServletResponse, ex: Exception) =
            ResponseEntity(
                    PayLoadError("Promotion Not Found", ex.message!!),
                    HttpStatus.NOT_FOUND
            )
}