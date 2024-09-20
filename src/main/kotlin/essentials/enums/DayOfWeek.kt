package essentials.enums.dayofweek

enum class DayOfWeek(val dayName: String, val isWeekend: Boolean = false) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday", true),
    SUNDAY("Sunday", true);

    fun nextDay() = entries[(ordinal+1) % entries.size]
}

fun main() {
    val friday: DayOfWeek = DayOfWeek.FRIDAY
    println(friday.dayName) // Friday
    println(friday.isWeekend) // false
    val saturday: DayOfWeek = friday.nextDay()
    val sunday = DayOfWeek.SUNDAY
    val monday: DayOfWeek = sunday.nextDay()
    println(sunday.dayName)
    println(monday.dayName)
    println(saturday.dayName) // Saturday
    println(saturday.isWeekend) // true
}
