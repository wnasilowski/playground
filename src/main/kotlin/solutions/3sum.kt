package solutions.threesum

fun run() {
    val solution = Solution()
    val input = intArrayOf(-1,0,1,2,-1,-4)
    println("input: ${input.toList()}")
    val result = solution.threeSum(input)
    println("result: $result")
}

class Solution {
    fun threeSum(nums: IntArray): List<List<Int>> {
        if (nums.size < 3) {
            return emptyList()
        }
        val result = hashSetOf<List<Int>>()
        nums.sort()
        for(i in 0..(nums.size-2)) { 
            var j: Int = i + 1
            var k: Int = nums.size - 1
            while(j<k) {
                val sum = nums[i] + nums[j] + nums[k]
                when {
                    sum == 0 -> {
                        result.add(listOf(nums[i], nums[j], nums[k]))
                        j++
                    }
                    sum < 0 -> j++
                    sum > 0 -> k--
                }
            }
        }
        return result.toList()
    }
}