package solutions.longestsubstring

fun run() {
    val solution = Solution()
    val input = "abcabcbb"
    println("input: ${input}")
    val result = solution.lengthOfLongestSubstring(input)
    println("result: $result")
}

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        if (s.length <= 0) {
            return s.length
        }
        var maxLength = 0
        var left = 0
        var right = 0
        val existing = hashMapOf<Char, Int>()
        while (right < s.length) {
            val unique = !existing.containsKey(s.get(right))
            if (unique) {
                existing[s.get(right)] = right
                val currentLength = right - left + 1
                maxLength = maxOf(maxLength, currentLength)
                right++
            } else {
                left = existing[s.get(right)]!! + 1
                right = left
                existing.clear()
            }
        }
        return maxLength
    }
}