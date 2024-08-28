package solutions.targetsum

fun run() {
    val solution = Solution()
    val nums = intArrayOf(1,1,1,1,1)
    val target = 3
    println("target: $target nums: ${nums.toList()}")
    val result = solution.findTargetSumWays(nums, target)
    println("result: $result")
}

private data class CacheInput(
    val i: Int,
    val sum: Int, 
)

class Solution {
    lateinit private var nums: IntArray
    private var target: Int = 0
    private val cacheMap = mutableMapOf<CacheInput, Int>()
    fun findTargetSumWays(nums: IntArray, target: Int): Int {
        if (nums.size == 0) {
            return 0
        } 
        this.nums = nums
        this.target = target
        val resultsCount = recurse(0, nums[0], 0) + recurse(0, nums[0]*(-1), 0 )
        println(cacheMap.values)
        return resultsCount
    }

    private fun recurse(i: Int, sum: Int, count: Int): Int {
        if (i == nums.size -1) {
            if(sum == target) {
                return count + 1
            }
            return count
        }
        val cached = cacheMap[CacheInput(i, sum)] 
        if (cached != null) {
            return cached
        } else {
            val calculated = recurse(i+1, sum + nums[i+1], count) + recurse(i+1, sum - nums[i+1], count)
            cacheMap[CacheInput(i, sum)] = calculated
            return calculated
        } 
    }
}
/*
brute force
class Solution {
    lateinit private var nums: IntArray
    private var target: Int = 0

    fun findTargetSumWays(nums: IntArray, target: Int): Int {
        if (nums.size == 0) {
            return 0
        } 
        this.nums = nums
        this.target = target
        val resultsCount = recurse(0, nums[0], 0) + recurse(0, nums[0]*(-1), 0 )
        return resultsCount
    }

    private fun recurse(i: Int, sum: Int, count: Int): Int {
        //println("i: $i sum: $sum")
        if (i == nums.size -1) {
            if(sum == target) {
                return count + 1
            }
            return count
        }
        return recurse(i+1, sum + nums[i+1], count) + recurse(i+1, sum - nums[i+1], count)
    }
}
*/