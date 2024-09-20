package essentials.extensions.user

import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

// TODO

data class User(
    val username: String,
    val email: Email,
    val registrationDate: LocalDateTime,
    val height: Centimeters,
)

private val FORMATTER = DateTimeFormatter.ISO_DATE_TIME

data class Email(val value: String)

data class Centimeters(val value: Int)

data class UserJson(
    val username: String,
    val email: String,
    val registrationDate: String,
    val heightCm: Int,
)

fun User.toUserJson() = UserJson(
    username,
    email.value,
    registrationDate.format(FORMATTER),
    height.value,
)

val Int.cm
    get() = Centimeters(this)

fun UserJson.toUser() = User(
    username,
    Email(email),
    LocalDateTime.parse(registrationDate, FORMATTER),
    heightCm.cm,
)

fun main() {
    val user = User(
        username = "alex",
        email = Email("alex@example.com"),
        registrationDate = LocalDateTime
            .of(1410, 7, 15, 10, 13),
        height = 170.cm,
    )
    val userJson = user.toUserJson()
    println(userJson)
    // UserJson(username=alex, email=alex@example.com,
    // registrationDate=1410-07-15T10:13, heightCm=170)
    val user2 = userJson.toUser()
    println(user2) // User(username=alex,
    // email=Email(value=alex@example.com),
    // registrationDate=1410-07-15T10:13,
    // height=Centimeters(value=170))
}

class DataConversionTest {

    @Test
    fun `test User to UserJson conversion`() {
        val user = User(
            username = "alex",
            email = Email("alex@example.com"),
            registrationDate = LocalDateTime.now(),
            height = 170.cm
        )

        val userJson = user.toUserJson()

        assertEquals("alex", userJson.username)
        assertEquals("alex@example.com", userJson.email)
        assertEquals(user.registrationDate.toString(), userJson.registrationDate)
        assertEquals(user.height.value, userJson.heightCm)
    }

    @Test
    fun `test UserJson to User conversion`() {
        val userJson = UserJson(
            username = "alex",
            email = "alex@example.com",
            registrationDate = LocalDateTime.now().toString(),
            heightCm = 170
        )

        val user = userJson.toUser()

        assertEquals("alex", user.username)
        assertEquals("alex@example.com", user.email.value)
        assertEquals(userJson.registrationDate, user.registrationDate.toString())
        assertEquals(userJson.heightCm.cm, user.height)
    }

    @Test
    fun `test cm extension property`() {
        val value = 150
        val cm = value.cm

        assertEquals(value, cm.value)
    }
}
