package solutions.countcompletetree2

fun run() {
    val input = listOf(1)
    println("input: $input")
    val nodes = mutableListOf<TreeNode>()
    nodes.add(TreeNode(-1))
    input.forEach {
        nodes.add(TreeNode(it))
    }

    for(i in 1 .. nodes.size - 1) {
        nodes[i].left = nodes.getOrNull(i * 2)
        nodes[i].right = nodes.getOrNull(i * 2 + 1)
    }
    val result = Solution().countNodes(nodes[1])
    println("result: $result")
}

 class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
 }
 
class Solution {
    fun countNodes(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val treeHeight = treeHight(root)
        if (treeHeight == 0) {
            return 1
        }
        val completeSize = Math.pow(2.toDouble(), treeHeight.toDouble() + 1).toInt() - 1
        val missing = countMissing(root, treeHeight, 0)
        return completeSize - missing
    }
    private fun treeHight(root: TreeNode?): Int {
        var i = 0
        var current = root?.left
        while(current != null) {
            i++
            current = current.left
        }
        return i
    }

    private fun countMissing(root: TreeNode, height: Int, i: Int): Int {
        var res = 0
        if (i == height - 1) {
            if (root.right == null) {
                res++
            }
            if (root.left == null) {
                res++
            }
        } else {
            res += countMissing(root.right!!, height, i+1)
            res += countMissing(root.left!!, height, i+1)
        }
        return res
    }
}