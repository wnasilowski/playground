package solutions.removeduplicates

fun run() {
    val solution = Solution()
    val input = listOf(1,1,1,2,3).toLinked()

    println("input:")
    printList(input)


   val result = solution.deleteDuplicates(input)
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
    fun deleteDuplicates(head: ListNode?): ListNode? {
        var result = head
        var current = head
        var previous: ListNode? = null
        while(current != null) {
            val currentValue = current.`val` 
            if (currentValue == current.next?.`val`) {
                do {
                    current = current?.next
                } while(current?.`val` == currentValue)
                if (previous == null) {
                    result = current
                } else {
                    previous.next = current
                }
            } else {
                previous = current
                current = current.next
            }
        } 
        return result
    }
}