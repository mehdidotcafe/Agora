package io.agora.agora.entities

open class Callback {
    open fun success(obj: Any?) {

    }

    open fun fail(error: Int) {

    }

    open fun fail(error: String) {

    }
}