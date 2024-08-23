package solutions.rotateright

fun run() {
    val solution = Solution()
    val input = listOf(1, 2, 3).toLinked()
    val k = 4
    println("k: $k input:")
    printList(input)

   val result = solution.rotateRight(input, k)
   println("Result:")
   printList(result)
}

private fun List<Int>.toLinked(): ListNode? {
    var result: ListNode? = null
    var current: ListNode? = null
    forEach {
        val newNode = ListNode(it)
        if (current == null) {
            current = newNode
            result = current
        } else {
            current!!.next = newNode
            current = current!!.next
        }
    }
    return result
}

private fun printList(head: ListNode?) {
    var node = head
    print("<")
    while(node != null) {
        print("${node.`val`}, ")
        node = node.next
    }
    print(">\n")
}


class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class Solution {
    fun rotateRight(head: ListNode?, k: Int): ListNode? {
        val n = head.listSize()
        if (n <= 1) {
            return head
        }
        val effectiveK = k % n
        if (effectiveK == 0) {
            return head
        }
        val newTailIndex = n - 1 - effectiveK
        val newHeadIndex = n - effectiveK
        val nodes = head.findItemsByIndex(listOf(newTailIndex, newHeadIndex, n - 1))
        nodes[newTailIndex]?.next = null
        nodes[n - 1]?.next = head
        return nodes[newHeadIndex]
    }

    private fun ListNode?.listSize(): Int {
        var current = this
        var n = 0
        while (current != null) {
            n++
            current = current.next
        }
        return n
    }
    private fun ListNode?.findItemsByIndex(indexes: List<Int>): Map<Int, ListNode> {
        var current = this
        var i = 0
        val result = mutableMapOf<Int, ListNode>()
        while (current != null) {
            if (indexes.contains(i)) {
                result[i] = current
            }
            i++
            current = current.next
        }
        return result
    }
}