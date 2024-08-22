package solutions.addtwonumbers

fun run() {
    val solution = Solution()
    val input1 = listOf(2, 4, 3).toLinked()
    val input2 = listOf(5, 6, 4).toLinked()

    println("input1:")
    printList(input1)
    println("input2:")
    printList(input2)

   val result = solution.addTwoNumbers(input1, input2)
   println("Result:")
   printList(result)
}

class ListNode(var `val`: Int) {
     var next: ListNode? = null
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

class Solution {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var node1: ListNode? = l1
        var node2: ListNode? = l2
        var result: ListNode? = null
        var current: ListNode? = null
        var leftOver = 0
        while(node1 != null || node2 != null || leftOver != 0) {
            val currentValue = (node1?.`val` ?: 0) + (node2?.`val` ?: 0) + leftOver
            leftOver = currentValue / 10
            val newValue = currentValue % 10
            val newNode = ListNode(newValue)
            if (current == null) {
                current = newNode
                result = current
            } else {
                current.next = newNode
                current = current.next
            }
            node1 = node1?.next
            node2 = node2?.next
        }
        return result
    }
}