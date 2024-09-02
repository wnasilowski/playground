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
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        if (text1.length == 0 || text2.length == 0) {
            return 0
        }
        val m = text1.length
        val n = text2.length
        val memo = Array(m+1) { IntArray(n+1) { 0 } }
        text1.forEachIndexed { i, _ ->
            text2.forEachIndexed { j, _ ->
                memo[i+1][j+1] = if (text1[i] == text2[j]) {
                    memo[i][j]+1
                } else {
                     maxOf(memo[i+1][j], memo[i][j+1])
                }
            }
         }
         return memo[m][n]
    }
}
/*
With memo
class Solution {
    lateinit private var text1: String
    lateinit private var text2: String
    lateinit private var memo: Array<IntArray>
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        if (text1.length == 0 || text2.length == 0) {
            return 0
        }
        this.text1 = text1
        this.text2 = text2
        memo = Array(text1.length) { IntArray(text2.length) { -1 } }
        return recurseWithMemo(0,0)
    }
    private fun recurseWithMemo(i: Int, j: Int): Int {
        if (i >= text1.length || j >= text2.length) {
            return 0
        }
        val cached = memo[i][j]
        if(cached != -1) {
            return cached
        }
        val result = if (text1[i] == text2[j]) {
            recurseWithMemo(i+1, j+1)+1
        } else {
            maxOf(recurseWithMemo(i+1, j), recurseWithMemo(i, j+1))
        }
        memo[i][j] = result
        return result
    }
}
     */