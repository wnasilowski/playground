package solutions.trie

fun run() {
    
}

class TrieNode() {
    val children: MutableMap<Char, TrieNode> = HashMap()
    var endOfWord: Boolean = false
}

class Trie() {
    val root = TrieNode()
    fun insert(word: String) {
        var currentNode = root
        word.forEach { letter ->
            if(!currentNode.children.containsKey(letter)){
                currentNode.children[letter] = TrieNode()
            }
            currentNode = currentNode.children[letter]!!
        }   
        currentNode.endOfWord = true     
    }

    fun search(word: String): Boolean {
        var currentNode = root
        word.forEach { letter ->
            if(!currentNode.children.containsKey(letter)){
                return false
            }
            currentNode = currentNode.children[letter]!!
        }   
        return currentNode.endOfWord
    }

    fun startsWith(prefix: String): Boolean {
        var currentNode = root
        prefix.forEach { letter ->
            if(!currentNode.children.containsKey(letter)){
                return false
            }
            currentNode = currentNode.children[letter]!!
        }   
        return true
    }
}