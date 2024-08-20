package solutions.minimumsubarray

fun run() {
    val solution = Solution()
    val input = intArrayOf(1,4,4)
    val target = 4
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
        while(end < nums.size) {
            val sum = sum(start, end, nums)
            if (sum >= target) {
                val currentLength = end - start + 1
                minLength = minOf(currentLength, minLength)
                if (minLength == 1) {
                    return 1
                }
                start++
                if (start > end) {
                    end = start
                }
            } else {
                end++
            }
        }
        return if (minLength == Int.MAX_VALUE) {
            0
        } else {
            minLength
        }
   }
   private fun sum(start: Int, end: Int, nums: IntArray): Int {
        var result = 0
        for (i in start .. end) { 
            result += nums[i]
        }
        return result
   }
}