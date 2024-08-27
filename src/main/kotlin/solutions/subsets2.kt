package solutions.subsets2

fun run() {
    val solution = Solution()
    val input = intArrayOf(1,2,2)
    println("input (repetitions): ${input.toList()}")
    val result = solution.subsetsWithDup(input)
    println("result: $result")
}

class Solution() {
    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        nums.sort()
        recursiveTracking(0, emptyList(), nums, result)
        return result
    }

    fun recursiveTracking(i: Int, currentSubset: List<Int>, nums: IntArray, result: MutableList<List<Int>>) {
        if (i >= nums.size) {
            result.add(currentSubset)
            return
        }
        recursiveTracking(i+1, currentSubset + nums[i], nums, result)
        var j = i
        while(j < nums.size - 1 && nums[j] == nums[j+1]) {
            j += 1
        }
        recursiveTracking(j+1, currentSubset, nums, result)
    }
}