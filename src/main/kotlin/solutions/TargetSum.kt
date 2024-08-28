package solutions.targetsum

fun run() {
    val solution = Solution()
    val nums = intArrayOf(1,1,1,1,1)
    val target = 3
    println("target: $target nums: ${nums.toList()}")
    val result = solution.findTargetSumWays(nums, target)
    println("result: $result")
}

class Solution {
    lateinit private var nums: IntArray
    private var target: Int = 0
    private var resultsCount = 0
    fun findTargetSumWays(nums: IntArray, target: Int): Int {
        if (nums.size == 0) {
            return 0
        } 
        this.nums = nums
        this.target = target
        recurse(0, nums[0])
        recurse(0, nums[0]*(-1))
        return resultsCount
    }

    private fun recurse(i: Int, sum: Int) {
        //println("i: $i sum: $sum")
        if (i == nums.size -1) {
            if(sum == target) {
                resultsCount++
            }
            return
        }
        recurse(i+1, sum + nums[i+1])
        recurse(i+1, sum - nums[i+1])
    }
}