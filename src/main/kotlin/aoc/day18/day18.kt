package aoc.day18

import aoc.utils.readInput

fun main() { 
    val input = readInput("day18/Day18")
    part1(input)
    //part2(input)
}

fun part1(input: List<String>) {
    val size = 71
    val bytes = 1
    val obstacles = input.map { line ->
        val lineElements = line.split(",")
        val x = lineElements[0].toInt()
        val y = lineElements[1].toInt()
        Coordinates(x, y)
    }.take(bytes)

    val start = Coordinates(0,0)
    val end = Coordinates(size-1,size-1)
    val queue = mutableListOf(start)
    val visited = mutableSetOf<Coordinates>()
    val parents = mutableMapOf<Coordinates, Coordinates>()
    while (queue.isNotEmpty()) {
        //println("queue: $queue")
        val current = queue.removeLast()
        visited.add(current)

        //println("Visiting: $current")
        if (current == end) {
            val path = getPath(start, end, parents)
            //println("Found path: $path")
            println("Finished after visiting ${path.size}")
            break
        }
        VECTORS.forEach { vector ->
            val newPosition = current + vector
            //println("new position proposal: $newPosition")
            if(!obstacles.contains(newPosition) && newPosition.inBounds(size) && !visited.contains(newPosition) && !parents.contains(newPosition)) {
                //println("adding $newPosition")
                queue.add(newPosition)
                parents[newPosition] = current
            }
        }
    }
    println("Finished way")
}

fun part2(input: List<String>) {
}



private data class Coordinates(
    val x: Int,
    val y: Int,
) {
    operator fun plus(other: Coordinates): Coordinates {
        return Coordinates(x + other.x, y + other.y)
    }
}

private fun Coordinates.inBounds(size: Int) = x >= 0 && x < size && y >= 0 &&y < size

private val VECTORS = listOf(
    Coordinates(0,1),
    Coordinates(1,0),
    Coordinates(0,-1),
    Coordinates(-1,0),
)