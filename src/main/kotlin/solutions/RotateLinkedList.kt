package solutions.rotateright

fun run() {
    val solution = Solution()
    val input = listOf(1, 2, 3, 4, 5).toLinked()
    val k = 10
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
        if (head == null || k == 0) {
            return head
        }

        var current = head
        val arrayList = mutableListOf<ListNode>()
        while(current != null) {
            arrayList.add(current)
            current = current.next
        }
        val n = arrayList.size
        val offset = k % n
        if (offset == 0 || n == 1) {
            return head
        }
        arrayList[n - offset - 1].next = null
        arrayList[n-1].next = arrayList[0]
        val newHeadIndex = n - offset
        return arrayList[newHeadIndex]
    }
}