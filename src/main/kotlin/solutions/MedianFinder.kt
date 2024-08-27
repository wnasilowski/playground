package solutions.medianfinder

import java.util.PriorityQueue

fun run() {
    val finder = MedianFinder()
    val input = listOf(1,2)
    input.forEach { 
        finder.addNum(it)
    }
    val result1 = finder.findMedian()
    println("result: $result1")
    finder.addNum(3)
    val result2 = finder.findMedian()
    println("result: $result2")

}

class ReversedPriorityQueue() {
    private val queue = PriorityQueue<Int>()
    fun poll(): Int = queue.poll() * -1
    fun peek(): Int = queue.peek() * -1
    fun add(element: Int) = queue.add(element * -1)
    fun isEmpty() = queue.isEmpty()
    val size: Int
    get() = queue.size
    override fun toString(): String {
        return queue.toList().map {
            it * -1
        }.toString()
    }
}

class MedianFinder() {
    private val smallQueue = ReversedPriorityQueue()
    private val largeQueue =  PriorityQueue<Int>()
    fun addNum(num: Int) {
        smallQueue.add(num)
        if (!smallQueue.isEmpty() && !largeQueue.isEmpty() && smallQueue.peek() > largeQueue.peek()) {
            val movedElement = smallQueue.poll()
            largeQueue.add(movedElement)
        }

        if (smallQueue.size > largeQueue.size+1) {
            val movedElement = smallQueue.poll()
            largeQueue.add(movedElement)
        }
        if (largeQueue.size > smallQueue.size+1) {
            val movedElement = largeQueue.poll()
            smallQueue.add(movedElement)
        }
    }

    fun findMedian(): Double {
        return when  {
            smallQueue.size == largeQueue.size -> (smallQueue.peek() + largeQueue.peek())/2.0
            smallQueue.size > largeQueue.size -> smallQueue.peek().toDouble()
            else -> largeQueue.peek().toDouble()
        }
    }
}
