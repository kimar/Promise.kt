package io.kida.promise

typealias Resolver<T> = (
        resolve: (T) -> Unit,
        reject: (Throwable) -> Unit
) -> Unit

public class Promise<T>(val resolver: Resolver<T>) {

    fun then(closure: (T) -> Unit) {
        resolver({ closure(it) }, {})
    }

    fun <U>then(closure: (T) -> Promise<U>): Promise<U> {
        var promise: Promise<U>? = null
        resolver({ promise = closure(it) }, {})
        return Promise<U> { resolve, reject ->
            promise?.then { resolve(it)}
            promise?.fail { reject(it) }
        }
    }

    fun fail(closure: (Throwable) -> Unit) {
        resolver({}, { closure(it) })
    }
}