package solutions.backspacecompare

import java.util.ArrayDeque

fun run() {
    val solution = Solution()
    val s = "ab###c"
    val t = "ad#c"
    val result = solution.backspaceCompare(s, t)
    println("result: $result")
}

class Solution { 
    private fun String.toStack(): ArrayDeque<Char> {
        val stack = ArrayDeque<Char>()
        forEach { 
            if(it == '#') {
                try {
                    stack.pop()
                } catch(e: java.util.NoSuchElementException) {
                    //it is ok
                }
            } else {
                stack.push(it)
            }
        }
        return stack
    }
    fun backspaceCompare(s: String, t: String): Boolean {
        val sStack = s.toStack().toList()
        val tStack = t.toStack().toList()
        return sStack == tStack
    }
}