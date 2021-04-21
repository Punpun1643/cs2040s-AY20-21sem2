/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
        TreeNode focusedRoot = null;
        if (child == Child.LEFT) {
            focusedRoot = node.left;
        }
        else {
            focusedRoot = node.right;
        }

        if (focusedRoot == null) {
            return 0;
        }
        else {
            return 1 + countNodes(focusedRoot, Child.LEFT) + countNodes(focusedRoot, Child.RIGHT);

        }
    }

    /**
     * Build an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    TreeNode[] enumerateNodes(TreeNode node, Child child) {
        // TODO: Implement this
//        int size = countNodes(node, child);
//        TreeNode[] outputArray = new TreeNode[size];
//
//        int indexCounter = 0;
//
//        return outputArray;
        //this is previous submission
        int size = countNodes(node, child);
        TreeNode[] outputArray = new TreeNode[size];

        TreeNode focusedNode = null;
        if (child == Child.LEFT) {
            focusedNode = node.left;
        } else {
            focusedNode = node.right;
        }
        int leftSize = countNodes(focusedNode, Child.LEFT);
        TreeNode[] leftArray;
        int rightSize = countNodes(focusedNode, Child.RIGHT);
        TreeNode[] rightArray;

        if (focusedNode.left != null) {
            leftArray = enumerateNodes(focusedNode, Child.LEFT);
            for (int i = 0; i < leftSize; i++) {
                outputArray[i] = leftArray[i];
            }
        }

        TreeNode root = focusedNode;
        outputArray[leftSize] = root;

        if (focusedNode.right != null) {
            rightArray = enumerateNodes(focusedNode, Child.RIGHT);
            for (int j = leftSize + 1; j < leftSize + 1 + rightSize; j++) {
                outputArray[j] = rightArray[j - leftSize - 1];
            }
        }

        return outputArray;
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        TreeNode root = null;
        int low = 0;
        int high = nodeList.length - 1;


        if (low > high) {
            return root;
        }

        int mid = low + (high - low) / 2;
        root = nodeList[mid];

        TreeNode[] leftNodes = new TreeNode[mid];
        for(int i = low; i < mid; i++) {
            leftNodes[i] = nodeList[i];
        }

        TreeNode[] rightNodes = new TreeNode[high - mid];
        for(int j = mid + 1; j <= high; j++) {
            rightNodes[j - (mid + 1)] = nodeList[j];
        }
        root.left = buildTree(leftNodes);
        root.right = buildTree(rightNodes);

        return root;

    }

    /**
    * Rebuild the specified subtree of a node.
    * 
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
    * Insert a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);
//        tree.root = new TreeNode(1);
//        tree.root.left = new TreeNode(2);
//        tree.root.right = new TreeNode(3);
//        tree.root.left.left = new TreeNode(4);
//        tree.root.left.right = new TreeNode(5);
//
//        int output = tree.countNodes(tree.root, Child.LEFT);
//        System.out.println(output);
//
//        TreeNode[] array;
//        int size = tree.countNodes(tree.root, Child.LEFT);
//        array = tree.enumerateNodes(tree.root, Child.LEFT);
//
//        System.out.println(array);
    }
}
