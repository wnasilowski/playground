package solutions.courseschedule

import kotlin.collections.reverse
import kotlin.intArrayOf

fun run() {
    val prerequisites: Array<IntArray> = arrayOf(
        intArrayOf(1,0),
        intArrayOf(2,0),
        intArrayOf(3,1),
        intArrayOf(3,2),
    )
    val numCourses = 4
    val solution = Solution()
    println("numCourses: $numCourses")
    println("prerequisites: ${prerequisites.map{it.toList()}.toList()}")
    val result = solution.findOrder(numCourses, prerequisites)
    println("result: ${result.toList()}")
}

class Node(val key: Int) {
    val adjList: MutableList<Node> = mutableListOf<Node>()
}

class Solution {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        if (numCourses == 0) {
            return IntArray(0)
        }

        val courses = mutableListOf<Node>()
        for(i in 0 .. numCourses - 1) {
            courses.add(Node(i))
        }
        prerequisites.forEach { connection ->
            courses[connection[1]].adjList.add(courses[connection[0]])
        }
        val visited = HashSet<Int>()
        val path =  HashSet<Int>()
        val result = mutableListOf<Int>()
        try {
            courses.forEach {
                dfs(it, visited, path, result)
            }
        } catch(e: IllegalArgumentException) {
            return IntArray(0)
        }
        result.reverse()
        return result.toIntArray()
    }

    private fun dfs(root: Node, visited: MutableSet<Int>, path: MutableSet<Int>, result: MutableList<Int>) {
        if (path.contains(root.key)) {
            throw IllegalArgumentException("Cycle detected")
        }
        if (visited.contains(root.key)) {
            return
        }
        visited.add(root.key)
        path.add(root.key)
        root.adjList.forEach { neighbour ->
            dfs(neighbour, visited, path, result)
        }
        path.remove(root.key)
        result.add(root.key)
    }
}