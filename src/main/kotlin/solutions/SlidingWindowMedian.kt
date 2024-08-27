package solutions.slidingwindowmedian

import kotlin.emptyArray
import kotlin.doubleArrayOf
import java.util.PriorityQueue

fun run() {
    val solution = Solution()
    val nums = intArrayOf(-2147483648,-2147483648,2147483647,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648)
    val k = 3
    println("input: ${nums.toList()}")
    val result = solution.medianSlidingWindow(nums, k).toList()
    println("result: $result")
}

class ReversedPriorityQueue() {
    private val queue = PriorityQueue<Long>()
    fun poll(): Long = queue.poll() * -1
    fun peek(): Long = queue.peek() * -1
    fun add(element: Long) = queue.add(element * -1)
    fun remove(element: Long) = queue.remove(element * -1)
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
    private val largeQueue =  PriorityQueue<Long>()
    fun addNum(num: Long) {
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

    fun removeNum(num: Long) {
        var removed = false
        if (num <= smallQueue.peek()) {
            removed = smallQueue.remove(num)
        }
        if(!removed && num >= largeQueue.peek()) {
            largeQueue.remove(num)
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
        //println("smallQueue $smallQueue largeQueue $largeQueue")
        return when  {
            smallQueue.size == largeQueue.size -> (smallQueue.peek()/2.0 + largeQueue.peek()/2.0)
            smallQueue.size > largeQueue.size -> smallQueue.peek().toDouble()
            else -> largeQueue.peek().toDouble()
        }
    }
}

class Solution {
    fun medianSlidingWindow(nums: IntArray, k: Int): DoubleArray {
        val finder = MedianFinder()
        if (nums.size < k) {
            return doubleArrayOf()
        }
        val result = DoubleArray(nums.size - k + 1)
        for (i in 0 .. k-1) {
           finder.addNum(nums[i].toLong())
        }
        var left = 0
        var right = left + k - 1
        while(true) {
            result[left] = finder.findMedian()
            //println("left $left right $right median ${finder.findMedian()}")
            //println("remove ${nums[left]}")
            finder.removeNum(nums[left].toLong())
            left++
            right++
            if (right >= nums.size){
                break
            }
            finder.addNum(nums[right].toLong())
            //println("add ${nums[right]}")

        }  
        return result
    }
}