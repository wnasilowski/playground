package solutions.longestcommonsubstring

fun run() {
    val solution = Solution()
    val text1 = "abcde"
    val text2 = "ace"
    println("text1: $text1 text2: $text2")
    val result = solution.longestCommonSubsequence(text1, text2)
    println("result: $result")
}

class Solution {
    lateinit private var text1: String
    lateinit private var text2: String
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        if (text1.length == 0 || text2.length == 0) {
            return 0
        }
        this.text1 = text1
        this.text2 = text2
        return recurse(0,0)
    }
    private fun recurse(i: Int, j: Int): Int {
        if (i >= text1.length || j >= text2.length) {
            return 0
        }
        if(text1[i] == text2[j]) {
            return recurse(i+1, j+1)+1
        } else {
            return maxOf(recurse(i+1, j), recurse(i, j+1))
        }
    }
}