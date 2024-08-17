package solutions.containers

fun run() {
    val solution = Solution()
    val input = intArrayOf(1,8,6,2,5,4,8,3,7)
    println("input: ${input.toList()}")
    val result = solution.maxArea(input)
    println("result: $result")
}

class Solution {
    fun maxArea(height: IntArray): Int {
        val n=height.size
        var left = 0
        var right = n - 1
        var maxArea = 0
        while (left<right) {
            val area = area(left, right, height)
            maxArea = maxOf(area, maxArea)
            if (height[left]<height[right]) {
                left++
            } else {
                right--
            }
        }
        return maxArea
    }
    
    private fun area(left: Int, right: Int, height: IntArray): Int {
       return (right-left) * minOf(height[left], height[right])
    }
}
