package advanced.delegates.lateinit

import org.junit.Test
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.test.assertEquals
import kotlin.test.assertIs

// TODO



class Lateinit<T> : ReadWriteProperty<Any?, T> {
    private var value: ValuesHolder<T> = ValuesHolder.Empty

    override fun getValue(
        thisRef: Any?,
        prop: KProperty<*>
    ): T = when (val v = value) {
        ValuesHolder.Empty ->
            error("Uninitialized lateinit property ${prop.name}")
        is ValuesHolder.Container -> v.storedValue
    }

    override fun setValue(
        thisRef: Any?,
        prop: KProperty<*>,
        value: T
    ) {
        this.value = ValuesHolder.Container(value)
    }

    private sealed interface ValuesHolder<out T> {
        data class Container<T>(val storedValue: T): ValuesHolder<T>
        data object Empty: ValuesHolder<Nothing>
    }
}
// Implement Lateinit delegate here
// Easy: Uncomment the first 2 tests and implement Lateinit so it works for Int type
// Medium: Uncomment the first 3 tests and implement Lateinit so it works for all non-nullable types
// Hard: Uncomment all tests and implement Lateinit so it works for all types

class LateinitTest {

    @Test
    fun `Throws exception if accessed before initialization`() {
        var value: Int by Lateinit()
        val res = runCatching {
            println(value)
        }
        val exception = assertIs<IllegalStateException>(res.exceptionOrNull())
        assertEquals("Uninitialized lateinit property value", exception.message)

        var value2: Int by Lateinit()
        val res2 = runCatching {
            println(value2)
        }
        val exception2 = assertIs<IllegalStateException>(res2.exceptionOrNull())
        assertEquals("Uninitialized lateinit property value2", exception2.message)
    }

    @Test
    fun `Behaves like a normal variable for Int`() {
        var value: Int by Lateinit()
        value = 10
        assertEquals(10, value)
        value = 20
        assertEquals(20, value)
    }

    @Test
    fun `Behaves like a normal variable for String`() {
        var value: String by Lateinit()
        value = "AAA"
        assertEquals("AAA", value)
        value = "BBB"
        assertEquals("BBB", value)
    }

    @Test
    fun `Behaves like a normal variable for nullable String`() {
        var value: String? by Lateinit()
        value = "AAA"
        assertEquals("AAA", value)
        value = null
        assertEquals(null, value)
        value = "BBB"
        assertEquals("BBB", value)
    }
}
