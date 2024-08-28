package solutions.countcompletetree

fun run() {
    val input = listOf(1,2,3,4,5,6)
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
        val elementsCount = HashMap<Int, Int>()
        dive(root, 0, elementsCount)
        val treeHeight = elementsCount.keys.max()
        val fullPartSize = Math.pow(2.toDouble(), treeHeight.toDouble()).toInt() - 1
        val elementsInLastLevel = elementsCount[treeHeight] ?: throw IllegalStateException("Not expected value")
        return fullPartSize + elementsInLastLevel
    }
    private fun dive(root: TreeNode?, i: Int, elementsCount: MutableMap<Int, Int>) {
        if (root == null) {
            return
        }
        elementsCount[i] = elementsCount.getOrDefault(i, 0) + 1
        root.left?.let { dive(it, i+1, elementsCount) }
        root.right?.let { dive(it, i+1, elementsCount) }
    }
}