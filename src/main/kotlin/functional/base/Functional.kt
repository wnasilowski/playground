package functional.base.functional

import kotlin.reflect.KClass
import kotlin.reflect.typeOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.Test

class FunctionsClassic {

    fun add(num1: Int, num2: Int): Int = num1 + num2

    fun printNum(num: Int) {
        print(num)
    }

    fun triple(num: Int): Int = num * 3

    fun produceName(name: String): Name = Name(name)

    fun longestOf(
            str1: String,
            str2: String,
            str3: String,
    ): String = maxOf(str1, str2, str3, compareBy { it.length })
}

data class Name(val name: String)

class AnonymousFunctionalTypeSpecified {
    val add: (Int, Int) -> Int = fun(num1, num2) = num1 + num2
    val printNum: (Int) -> Unit = fun(num) = print(num)
    val triple: (Int) -> Int = fun(num) = 3 * num
    val produceName: (String) -> Name = fun(name) = Name(name)
    val longestOf: (String, String, String) -> String =
            fun(str1, str2, str3) = maxOf(str1, str2, str3, compareBy { it.length })
}

class AnonymousFunctionalTypeInferred {
    val add = fun(num1: Int, num2: Int) = num1 + num2
    val printNum = fun(num: Int): Unit = print(num)
    val triple = fun(num: Int) = 3 * num
    val produceName = fun(name: String) = Name(name)
    val longestOf =
            fun(str1: String, str2: String, str3: String) = 
            maxOf(str1, str2, str3, compareBy { it.length })
}

class LambdaFunctionalTypeSpecified {
    val add: (Int, Int) -> Int = { num1, num2 -> num1 + num2 }
    val printNum: (Int) -> Unit = { num -> print(num) }
    val triple: (Int) -> Int =  { num -> 3 * num }
    val produceName: (String) -> Name = { name -> Name(name) }
    val longestOf: (String, String, String) -> String =
            { str1, str2, str3 -> maxOf(str1, str2, str3, compareBy { it.length }) }
}

class LambdaFunctionalTypeInferred {
    val add = { num1: Int, num2: Int -> num1 + num2 }
    val printNum = { num: Int -> print(num) }
    val triple =  { num: Int -> 3 * num }
    val produceName = { name: String -> Name(name) }
    val longestOf =
            { str1: String, str2: String, str3: String -> maxOf(str1, str2, str3, compareBy { it.length }) }
}

class LambdaUsingImplicitParameter {
    val add: (Int, Int) -> Int = { num1, num2 -> num1 + num2 }
    val printNum: (Int) -> Unit = {  print(it) }
    val triple: (Int) -> Int =  { 3 * it }
    val produceName: (String) -> Name = { Name(it) }
    val longestOf: (String, String, String) -> String =
            { str1, str2, str3 -> maxOf(str1, str2, str3, compareBy { it.length }) }

}

class FunctionalTest {

    @Test
    fun `AnonymousFunctionalTypeSpecified has correct property signatures`() {
        checkPropertySignatures(AnonymousFunctionalTypeSpecified::class)
    }

    @Test
    fun `AnonymousFunctionalTypeSpecified has correct property behavior`() {
        checkPropertyBehavior(AnonymousFunctionalTypeSpecified())
    }

    @Test
    fun `AnonymousFunctionalTypeInferred has correct property signatures`() {
        checkPropertySignatures(AnonymousFunctionalTypeInferred::class)
    }

    @Test
    fun `AnonymousFunctionalTypeInferred has correct property behavior`() {
        checkPropertyBehavior(AnonymousFunctionalTypeInferred())
    }

    @Test
    fun `LambdaFunctionalTypeSpecified has correct property signatures`() {
        checkPropertySignatures(LambdaFunctionalTypeSpecified::class)
    }

    @Test
    fun `LambdaFunctionalTypeSpecified has correct property behavior`() {
        checkPropertyBehavior(LambdaFunctionalTypeSpecified())
    }

    @Test
    fun `LambdaFunctionalTypeInferred has correct property signatures`() {
        checkPropertySignatures(LambdaFunctionalTypeInferred::class)
    }

    @Test
    fun `LambdaFunctionalTypeInferred has correct property behavior`() {
        checkPropertyBehavior(LambdaFunctionalTypeInferred())
    }

    @Test
    fun `LambdaUsingImplicitParameter has correct property signatures`() {
        checkPropertySignatures(LambdaUsingImplicitParameter::class)
    }

    @Test
    fun `LambdaUsingImplicitParameter has correct property behavior`() {
        checkPropertyBehavior(LambdaUsingImplicitParameter())
    }

    private fun checkPropertySignatures(
            classToCheck: KClass<*>,
            expectLongestOf: Boolean = true,
    ) {
        val c = classToCheck.members
        val properties =
                mutableMapOf(
                        "add" to typeOf<(Int, Int) -> Int>(),
                        "printNum" to typeOf<(Int) -> Unit>(),
                        "triple" to typeOf<(Int) -> Int>(),
                        "produceName" to typeOf<(String) -> Name>(),
                )
        if (expectLongestOf) {
            properties += "longestOf" to typeOf<(String, String, String) -> String>()
        }
        for ((propertyName, propertyType) in properties) {
            val propertyReference = c.find { it.name == propertyName }
            assertNotNull(propertyReference) { "Property $propertyName is missing" }
            assertEquals(
                    propertyType,
                    propertyReference.returnType,
                    "Property $propertyName has wrong type"
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> checkPropertyBehavior(
            instance: T,
            expectLongestOf: Boolean = true,
    ) {
        val members = instance::class.members
        val add = members.find { it.name == "add" }!!
        assertEquals(3, (add.call(instance) as (Int, Int) -> Int)(1, 2))
        assertEquals(12, (add.call(instance) as (Int, Int) -> Int)(4, 8))
        val printNum = members.find { it.name == "printNum" }!!
        (printNum.call(instance) as (Int) -> Unit)(42)
        val triple = members.find { it.name == "triple" }!!
        assertEquals(9, (triple.call(instance) as (Int) -> Int)(3))
        assertEquals(15, (triple.call(instance) as (Int) -> Int)(5))
        val produceName = members.find { it.name == "produceName" }!!
        assertEquals(Name("John"), (produceName.call(instance) as (String) -> Name)("John"))
        assertEquals(Name("Jane"), (produceName.call(instance) as (String) -> Name)("Jane"))
        if (expectLongestOf) {
            val longestOf = members.find { it.name == "longestOf" }!!
            assertEquals(
                    "abc",
                    (longestOf.call(instance) as (String, String, String) -> String)(
                            "a",
                            "ab",
                            "abc"
                    )
            )
            assertEquals(
                    "xyz",
                    (longestOf.call(instance) as (String, String, String) -> String)(
                            "x",
                            "xy",
                            "xyz"
                    )
            )
        }
    }
}
