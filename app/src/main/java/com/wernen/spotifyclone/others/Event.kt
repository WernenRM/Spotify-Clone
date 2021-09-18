package com.wernen.spotifyclone.others

open class Event<out T>(private val data: T) {

    var hasBeenHandled = false
        private set

    fun getContentINotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            data
        }
    }

    fun peekContent() = data

}
