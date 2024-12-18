package aoc.day18

import aoc.utils.readInput
import java.util.*

fun main() { 
    val input = readInput("day18/Day18")
    //part1(input)
    part2(input)
}

fun part1(input: List<String>) {
    val size = 71
    val bytes = 1024
    val obstacles = input.map { line ->
        val lineElements = line.split(",")
        val x = lineElements[0].toInt()
        val y = lineElements[1].toInt()
        Coordinates(x, y)
    }.take(bytes)
    val graph = Graph<Coordinates>()
    for(x in 0..<size) {
        for(y in 0..<size) {
            val startNode = Coordinates(x,y)
            if(!obstacles.contains(startNode) && startNode.inBounds(size)) {
                VECTORS.forEach { moveDirection ->
                    val endNode = startNode + moveDirection
                    if(!obstacles.contains(endNode) && endNode.inBounds(size)) {
                        graph.addEdge(startNode, endNode, 1)
                    }
                }
            }
        }
    }
    val start = Coordinates(0,0)
    val end = Coordinates(size-1,size-1)

    val distances = dijkstra(graph.edges, start)
    println("res: ${distances[end]}") 
}

fun part2(input: List<String>) {

    val size = 71
    val bytes = 1024
    val obstacles = input.map { line ->
        val lineElements = line.split(",")
        val x = lineElements[0].toInt()
        val y = lineElements[1].toInt()
        Coordinates(x, y)
    }
    for (i in 1024..<obstacles.size) {
        val graph = Graph<Coordinates>()
        val slicedObstacles = obstacles.take(i)
        println("checking for $i on ${obstacles.size}")
        val percent =  100.0 * i.toDouble() / (obstacles.size).toDouble()
        println("$percent%")
        for(x in 0..<size) {
            for(y in 0..<size) {
                val startNode = Coordinates(x,y)
                if(!slicedObstacles.contains(startNode) && startNode.inBounds(size)) {
                    VECTORS.forEach { moveDirection ->
                        val endNode = startNode + moveDirection
                        if(!slicedObstacles.contains(endNode) && endNode.inBounds(size)) {
                            graph.addEdge(startNode, endNode, 1)
                        }
                    }
                }
            }
        }
        val start = Coordinates(0,0)
        val end = Coordinates(size-1,size-1)
        val distances = dijkstra(graph.edges, start)
        if(distances[end] == null) {
            val coordinates = slicedObstacles.last()
            println("Result #2: ${coordinates.x},${coordinates.y}")
            break
        }

    }
    //    println("res: ${distances[end]}") 
}


class Graph<T> {
    val edges = mutableMapOf<T, List<Pair<T, Int>>>()
    fun addEdge(start: T, end: T, weight: Int) {
        edges[start] = edges.getOrDefault(start, emptyList()) + (end to weight)
    }
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

private fun <Node> dijkstra(graph: Map<Node, List<Pair<Node, Int>>>, start: Node): Map<Node, Int> {
    val distances = mutableMapOf<Node, Int>().withDefault { Int.MAX_VALUE }
    val priorityQueue = PriorityQueue<Pair<Node, Int>>(compareBy { it.second })
    val visited = mutableSetOf<Node>()

    priorityQueue.add(start to 0)
    distances[start] = 0

    while (priorityQueue.isNotEmpty()) {
        val (node, currentDist) = priorityQueue.poll()
        if (visited.add(node)) {
            graph[node]?.forEach { (adjacent, weight) ->
                val totalDist = currentDist + weight
                if (totalDist < distances.getValue(adjacent)) {
                    distances[adjacent] = totalDist
                    priorityQueue.add(adjacent to totalDist)
                }
            }
        }
    }
    return distances
}
