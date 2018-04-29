import io.kida.promise.Promise
import org.junit.Assert
import org.junit.Test

class PromiseTest {

    class AnError: Throwable()

    @Test
    fun testThen() {
        Promise<String> { resolve, reject ->
            resolve("OK")
        }.then {
            Assert.assertEquals("OK", it)
        }
    }

    @Test
    fun testThenPromise() {
        Promise<String> { resolve, reject ->
            resolve("OK")
        }.then<Boolean> {
            Assert.assertEquals("OK", it)
            return@then Promise<Boolean> { resolve, reject ->  
                resolve(true)
            }
        }.then {
            Assert.assertTrue(it)
        }
    }

    @Test
    fun testThenThenPromise() {
        Promise<String> { resolve, reject ->
            resolve("OK")
        }.then<Boolean> {
            Assert.assertEquals("OK", it)
            return@then Promise<Boolean> { resolve, reject ->
                resolve(true)
            }
        }.then<Int> {
            Assert.assertTrue(it)
            return@then Promise<Int> { resolve, reject ->
                resolve(42)
            }
        }.then {
            Assert.assertEquals(42, it)
        }
    }

    @Test
    fun testFail() {
        Promise<String> { resolve, reject ->
            reject(AnError())
        }.fail {
            Assert.assertTrue(it is AnError)
        }
    }

    @Test
    fun testThenFail() {
        Promise<String> { resolve, reject ->
            resolve("OK")
        }.then<Boolean> {
            Assert.assertEquals("OK", it)
            return@then Promise<Boolean> { resolve, reject ->
                reject(AnError())
            }
        }.fail {
            Assert.assertTrue(it is AnError)
        }
    }
}