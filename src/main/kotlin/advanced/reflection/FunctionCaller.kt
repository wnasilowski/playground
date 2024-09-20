package advanced.reflection.functioncaller

import org.junit.After
import org.junit.Test
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.test.assertEquals

class FunctionCaller {
    inline fun <reified T> setConstant(value: T) {
        setConstant(typeOf<T>(), value)
    }

    fun setConstant(type: KType, value: Any?) {
        TODO()
    }

    fun <T> call(function: KFunction<T>): T {
        TODO()
    }
}

fun printStrIntNum(str: String, int: Int, num: Number) {
    println("str: $str, int: $int, num: $num")
}

fun printWithOptionals(l: Long = 999, s: String) {
    println("l: $l, s: $s")
}

fun main() {
    val caller = FunctionCaller()
    caller.setConstant("ABC")
    caller.setConstant(123)
    caller.setConstant(typeOf<Number>(), 3.14)
    caller.call(::printStrIntNum)
    // str: ABC, int: 123, num: 3.14
    caller.call(::printWithOptionals)
    // l: 999, s: ABC
}

class FunctionCallerTest {
    var value: Any? = null
    fun callStr(str: String) {
        value = "callStr $str"
    }

    fun callInt(int: Int) {
        value = "callInt $int"
    }

    fun callStringInt(str: String, int: Int) {
        value = "callStringInt $str $int"
    }

    data class User(val id: Int, val name: String?, var surname: String?)

    fun callUser(user: User) {
        value = user
    }

    fun callWithDefault(c: Char, i: Int = 999, s: String = "XXX", l: Long) {
        value = "callWithDefault $c $i $s $l"
    }

    @After
    fun cleanUp() {
        value = null
    }

    @Test
    fun testWithString() {
        val caller = FunctionCaller()
        caller.setConstant("ABC")
        caller.call(::callStr)
        assertEquals("callStr ABC", value)
    }

    @Test
    fun testWithInt() {
        val caller = FunctionCaller()
        caller.setConstant(123)
        caller.call(::callInt)
        assertEquals("callInt 123", value)
    }

    @Test
    fun testWithStringInt() {
        val caller = FunctionCaller()
        caller.setConstant("DEF")
        caller.setConstant(456)
        caller.call(::callStringInt)
        assertEquals("callStringInt DEF 456", value)
    }

    @Test
    fun testWithUser() {
        val caller = FunctionCaller()
        val user = User(123, "DEF", "GHI")
        caller.setConstant(user)
        caller.call(::callUser)
        assertEquals(user, value)
    }

    @Test
    fun testIgnoredOptional() {
        val caller = FunctionCaller()
        caller.setConstant('Z')
        caller.setConstant(123L)
        caller.call(::callWithDefault)
        assertEquals("callWithDefault Z 999 XXX 123", value)
    }
}
