package solutions.subsets

fun run() {
    val solution = Solution()
    val input = intArrayOf(1,2,3)
    println("input: ${input.toList()}")
    val result = solution.subsets(input)
    println("result: $result")
}

class Solution() {
    fun subsets(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        recursiveTracking(0, emptyList(), nums, result)
        return result
    }

    fun recursiveTracking(i: Int, currentSubset: List<Int>, nums: IntArray, result: MutableList<List<Int>>) {
        if (i >= nums.size) {
            result.add(currentSubset)
            return
        }
        recursiveTracking(i+1, currentSubset, nums, result)
        recursiveTracking(i+1, currentSubset + nums[i], nums, result)
    }
}