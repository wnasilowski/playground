package pl.wnasilowski.examples.functional

fun m(i: Int): Int {
    print("m$i ")
    return i * i
}
fun f(i: Int): Boolean {
    print("f$i ")
    return i % 2 == 0
}

fun main() {
    val list = listOf(1, 2, 3, 4)
    list.map(::m).filter(::f) // ?
    list.filter(::f).map(::m) // ?
    val sequence = sequenceOf(1, 2, 3, 4)
    sequence.map(::m).filter(::f).toList() // ?
    sequence.map(::m).filter(::f) // ?
    sequence.map(::m).filter(::f).first() // ?
    sequence.filter(::f).map(::m).toList() // ?

    val sequence2 = list.asSequence().map(::m) // ?
    sequence2.toList() // ?
    sequence2.filter(::f).toList() // ?
}