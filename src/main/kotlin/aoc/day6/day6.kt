package aoc.day6

import aoc.utils.readInput

fun main() {
  
    val input = readInput("day6/Day06")
    val part1Result = part1(input)
    println("Part1 result: $part1Result")
    val part2Result = part2(input)
    println("Part2 result: $part2Result")
}


fun part1(input: List<String>): Int {
    var count = 0
    var (x, y) = findDevice(input)
    var direction: Char = input.get(x, y)
    val visited = mutableSetOf<Pair<Int, Int>>()
    while(true) {
        //println("position: $x - $y")
        val newField = visited.add(x to y)
        if(newField) {
            count++
        }
        val nextX = x + direction.toVector().x
        val nextY = y + direction.toVector().y
        if (!isInside(nextX, nextY, input)) {
            return count
        }
        val next = input.get(nextX, nextY)
        if (next == '#') {
            direction = direction.turn()
            //println("turning to $direction")
        } else {
            x = nextX
            y = nextY
        }
    }
}

fun part2(input: List<String>): Int {
    var count = 0
    var (x, y) = findDevice(input)
    val maxLength = run(x,y, input, 0)
    for (i in 1..maxLength) {
        if (run(x, y, input, i, maxLength) == Int.MAX_VALUE) {
            count++
        }
    }
    return count

}
val obstacles = mutableSetOf<Pair<Int, Int>>()
fun run(startX: Int, startY: Int, initialInput: List<String>, i: Int, max: Int = 0): Int {
    val input = initialInput.map { it.toCharArray() }
    var x = startX
    var y = startY
    var direction: Char = input.getEl(x, y)
    var pathLength = 0
    val visited = mutableMapOf<Pair<Int, Int>, Int>()
    var obstacle: Pair<Int, Int>? = null

    while(true) {
        pathLength++
        //println("pathLength $pathLength")
        val nextX = x + direction.toVector().x
        val nextY = y + direction.toVector().y
        if (!isInside(nextX, nextY, initialInput)) {
            return pathLength
        }
        if(i == pathLength && !(startX == nextX && startY == nextY)) {
            input.setEl(nextX, nextY, '#')
            obstacle = nextX to nextY
        }
        val next = input.getEl(nextX, nextY)
        if (next == '#') {
            direction = direction.turn()
            //println("turning to $direction")
        } else {
            x = nextX
            y = nextY
        }
        visited[x to y] = visited.getOrDefault(x to y, 0)+1
        if(visited.getOrDefault(x to y, 0) >= 7) {
            return if(obstacles.add(obstacle!!)) {
                Int.MAX_VALUE
            } else {
                -1
            }
        }
    }
}

fun isInside(x: Int, y: Int, input: List<String>) = 
x >= 0 && x < input.firstOrNull()?.length ?: 0 && y >= 0 && y < input.size

fun findDevice(input: List<String>): Pair<Int, Int> {
    var y = 0
    while (y < input.size) {
        val line = input[y]
        var x = 0
        while(x < line.length) {
            if (listOf('<', '>', '^', 'v').contains(line[x])) {
                return x to y
            }
            x++
        }
        y++
    }
    throw IllegalArgumentException("Not found")
}

fun List<String>.get(x: Int, y: Int) = this[y][x]
fun List<CharArray>.getEl(x: Int, y: Int) = this[y][x]
fun List<CharArray>.setEl(x: Int, y: Int, el: Char) {
    this[y][x] = el
} 

fun Char.toVector() = when(this) {
    '^' -> DirVect(0, -1)
    'v' -> DirVect(0, 1)
    '<' -> DirVect(-1, 0)
    '>' -> DirVect(1, 0)
    else -> throw IllegalArgumentException("No such direction")
}

fun Char.turn() = when(this) {
    '^' -> '>'
    '>' -> 'v'
    'v' -> '<'
    '<' -> '^'
    else -> throw IllegalArgumentException("No such direction")
}

data class DirVect( 
    val x: Int,
    val y: Int,
) 