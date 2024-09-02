package solutions.letterdigits

fun run() {
    val solution = Solution()
    val digits = "23"
    println("digits: $digits")
    val result = solution.letterCombinations(digits)
    println("result: $result")
}

class Solution {
    private val result = mutableListOf<String>()
    private lateinit var digits: String
    fun letterCombinations(digits: String): List<String> {
        if(digits.isEmpty()) {
            return emptyList()
        }
        this.digits = digits
        recurse(0, "")
        return result
    }

    private fun recurse(i: Int, output: String) {
        if (i >= digits.length) {
            result.add(output)
            return
        } 
        digitLetterMap[digits[i]]?.forEach { 
            recurse(i+1, output + it)
        }
    }
}

private val digitLetterMap = mapOf<Char, List<Char>>(
    '1' to emptyList<Char>(),
    '2' to listOf('a', 'b', 'c'),
    '3' to listOf('d', 'e', 'f'),
    '4' to listOf('g', 'h', 'i'),
    '5' to listOf('j', 'k', 'l'),
    '6' to listOf('m', 'n', 'o'),
    '7' to listOf('p', 'q', 'r', 's'),
    '8' to listOf('t', 'u', 'v'),
    '9' to listOf('w', 'x', 'y', 'z'),
)