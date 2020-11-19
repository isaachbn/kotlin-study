package com.acme.tour.helper

import org.mockito.Mockito

object MockitoHelper {
    inline fun <reified T: Any> mockGenericClass() = Mockito.mock(T::class.java)

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T =  null as T
}