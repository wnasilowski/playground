package solutions.wordsearch

fun run() {
    val words = listOf("a", "a",)
    println("input: $words")
    val phrases = listOf("a", "aa", "a",".a", "a.")
    val wordDictionary = WordDictionary()
    words.forEach { wordDictionary.addWord(it) }
    phrases.forEach {
        val result = wordDictionary.search(it)
        println("Search: $it - $result")
    }
}

class TrieNode() {
    val children: MutableMap<Char, TrieNode> = HashMap()
    var endOfWord: Boolean = false
}

class WordDictionary() {
    val root = TrieNode()
    fun addWord(word: String) {
        var currentNode = root
        word.forEach { letter ->
            if (!currentNode.children.containsKey(letter)) {
                currentNode.children[letter] = TrieNode()
            }
            currentNode = currentNode.children[letter]!!
        }
        currentNode.endOfWord = true
    }

    fun search(word: String): Boolean {
        return recursiveSearch(root, word)
    }

    private fun recursiveSearch(node: TrieNode, word: String): Boolean {
        var currentNode = node
        word.forEachIndexed { i, letter ->
            if (letter == '.') {
                var result = false
                currentNode.children.forEach {
                    if (recursiveSearch(it.value, word.substring(i+1))) {
                        result = true
                    }
                }
                return result
            } else {
                if (!node.children.containsKey(letter)) {
                    return false
                }
                val res2 =  recursiveSearch(currentNode.children[letter]!!, word.substring(i+1))
                return res2
            }
        }
        return currentNode.endOfWord
    }
}
