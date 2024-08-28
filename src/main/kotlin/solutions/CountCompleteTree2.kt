package solutions.countcompletetree2

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

        return fullPartSize + elementsInLastLevel
    }
    private fun dive(root: TreeNode?, i: Int, elementsCount: MutableMap<Int, Int>) {

    }
}