package solutions.splitarray

import kotlin.comparisons.naturalOrder

fun run() {
    val solution = Solution()
    val input = intArrayOf(7,2,5,10,8)
    val k = 2
    println("input: ${input.toList()}, k: $k")
    val result = solution.splitArray(input, k)
    println("split array: result: $result")
}

class Solution {
    lateinit private var nums: IntArray
    var k: Int = -1
    fun splitArray(nums: IntArray, k: Int): Int {
        this.nums = nums
        this.k = k
        return recurse(0, emptyList())
    }

    private fun recurse(i: Int, splits: List<Int>): Int {
        if (splits.size + 1 == k) {
            return splitAndSum(nums.toList(), splits).max()
        }
        
        if(i+1 >= nums.size) {
            return Int.MAX_VALUE
        }

        val noSplitSum = recurse(i+1, splits)
        val splitSum = recurse(i+1, splits + listOf(i+1))
        return (minOf(noSplitSum, splitSum))
    }
    private fun splitAndSum(array: List<Int>, splitPoints: List<Int>): List<Int> {
        val splitIndices = splitPoints.sorted()
        val subarrays = mutableListOf<List<Int>>()
    
        var startIndex = 0
        for (splitIndex in splitIndices) {
            subarrays.add(array.subList(startIndex, splitIndex))
            startIndex = splitIndex
        }
        subarrays.add(array.subList(startIndex, array.size))
    
        return subarrays.map { it.sum() }
    }
}