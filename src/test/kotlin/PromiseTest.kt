import io.kida.promise.Promise
import org.junit.Assert
import org.junit.Test

class PromiseTest {

    class AnError: Throwable()

    @Test
    fun testThen() {
        Promise<String> { resolve, _ ->
            resolve("OK")
        }.then {
            Assert.assertEquals("OK", it)
        }
    }

    @Test
    fun testThenPromise() {
        Promise<String> { resolve, _ ->
            resolve("OK")
        }.thenWith {
            Assert.assertEquals("OK", it)
            return@thenWith Promise<Boolean> { resolve, _ ->
                resolve(true)
            }
        }.then {
            Assert.assertTrue(it)
        }
    }

    @Test
    fun testThenThenPromise() {
        Promise<String> { resolve, _ ->
            resolve("OK")
        }.thenWith {
            Assert.assertEquals("OK", it)
            return@thenWith Promise<Boolean> { resolve, _ ->
                resolve(true)
            }
        }.thenWith {
            Assert.assertTrue(it)
            return@thenWith Promise<Int> { resolve, _ ->
                resolve(42)
            }
        }.then {
            Assert.assertEquals(42, it)
        }
    }

    @Test
    fun testFail() {
        Promise<String> { _, reject ->
            reject(AnError())
        }.fail {
            Assert.assertTrue(it is AnError)
        }
    }

    @Test
    fun testThenFail() {
        Promise<String> { resolve, _ ->
            resolve("OK")
        }.thenWith {
            Assert.assertEquals("OK", it)
            return@thenWith Promise<Boolean> { _, reject ->
                reject(AnError())
            }
        }.fail {
            Assert.assertTrue(it is AnError)
        }
    }
}