package solutions.maxsumpath

fun run() {
    
}

 class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
 }
 
class Solution {
    private var maxSum = Int.MIN_VALUE
    fun maxPathSum(root: TreeNode?): Int {
        gainFromSubTree(root)
        return maxSum
    }
    private fun gainFromSubTree(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val gainFromLeft = maxOf(gainFromSubTree(root.left), 0)
        val gainFromRight = maxOf(gainFromSubTree(root.right), 0)
        maxSum = maxOf(maxSum, gainFromLeft+root.`val` + gainFromRight)
        return maxOf(gainFromRight + root.`val`, gainFromLeft + root.`val`)
    }
}