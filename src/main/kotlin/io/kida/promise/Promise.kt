package io.kida.promise

typealias Resolver<T> = (
        resolve: (T) -> Unit,
        reject: (Throwable) -> Unit
) -> Unit

public class Promise<out T>(private val resolver: Resolver<T>) {

    fun then(closure: (T) -> Unit) {
        resolver({ closure(it) }, {})
    }

    fun <U>thenWith(closure: (T) -> Promise<U>): Promise<U> {
        var promise: Promise<U>? = null
        resolver({ promise = closure(it) }, {})
        return Promise { resolve, reject ->
            promise?.then { resolve(it)}
            promise?.fail { reject(it) }
        }
    }

    fun fail(closure: (Throwable) -> Unit) {
        resolver({}, { closure(it) })
    }
}