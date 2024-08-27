package solutions.slidingwindowmedianslow

import kotlin.emptyArray
import kotlin.doubleArrayOf

fun run() {
    val solution = Solution()
    val nums = intArrayOf(2147483647,2147483647)
    val k = 2
    println("input: ${nums.toList()}")
    val result = solution.medianSlidingWindow(nums, k).toList()
    println("result: $result")
}
class Solution {
    fun medianSlidingWindow(nums: IntArray, k: Int): DoubleArray {
        var right = -1
        if (nums.size < k) {
            return doubleArrayOf()
        }
        val result = DoubleArray(nums.size - k + 1)
        for(left in 0 .. nums.size - k) {
            right = left + k - 1
            val window = nums.slice(left .. right).sorted()
            val centralIndex = (k - 1) / 2
            val median = if (k % 2 == 1) {
                window[centralIndex].toDouble()
            } else {
                window[centralIndex] / 2.0 + window[centralIndex + 1] / 2.0
            }
            result[left] = median
        }   
        return result
    }
}