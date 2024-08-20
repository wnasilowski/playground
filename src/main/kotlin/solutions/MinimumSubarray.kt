package solutions.minimumsubarray

fun run() {
    val solution = Solution()
    val input = intArrayOf(2,3,1,2,4,3)
    val target = 7
    println("input: ${input.toList()}")
    val result = solution.minSubArrayLen(target, input)
    println("result: $result")
}

class Solution {
    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        if(nums.size == 0) {
            return 0
        }
        var minLength = Int.MAX_VALUE
        var start = 0
        var end = 0
        var sum = nums[0]
        while(end < nums.size) {
            if (sum >= target) {
                val currentLength = end - start + 1
                minLength = minOf(currentLength, minLength)
                if (minLength == 1) {
                    return 1
                }
                sum -= nums[start]
                start++
                if (start > end) {
                    end = start
                }
            } else {
                end++
                if (end >= nums.size) {
                    break
                }
                sum += nums[end]
            }
        }
        return if (minLength == Int.MAX_VALUE) {
            0
        } else {
            minLength
        }
   }
}