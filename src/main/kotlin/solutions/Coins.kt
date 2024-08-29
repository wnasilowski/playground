package solutions.coins

fun run() {
    val solution = Solution()
    val coins = intArrayOf(1,2,5)
    val amount = 11
    println("amount: $amount coins: ${coins.toList()}")
    val result = solution.coinChange(coins, amount)
    println("result: $result")
}

class Solution {
    private fun generateDp(amount: Int) = IntArray(amount+1) {
        if (it == 0) {
            0
        } else {
            MAX
        }
    }
    private val MAX = Int.MAX_VALUE - 1000
    fun coinChange(coins: IntArray, amount: Int): Int {
        var dp = generateDp(amount)
        coins.forEach { coin ->
            val currentDp = generateDp(amount)
            dp.forEachIndexed { i, _ ->
                val keep = dp[i]
                var include = MAX
                if (i - coin >= 0) {
                    include = currentDp[i - coin] + 1
                }
                currentDp[i] = minOf(keep, include)
            }
            dp = currentDp
        }
        return if(dp[amount] == MAX) {
            -1
        } else {
            dp[amount]
        }
    }
}