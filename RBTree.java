/**
 * This class will include the main functions:
 * Insert, Find, Delete for the red-black tree.
 * Helper methods will also be used for the main methods
 * and a private class for the trees nodes will be included.
 */
public class RBTree {

    private enum NodeColor{
        Red, Black; // Red=true; Black = false
    }

    /**
     * Creating private class for the nodes
     * in the tree
     */
    private class Node {
        int value;
        Node left;
        Node right;
        Node parent;
        NodeColor color;

        Node(int value) {
            this.value = value;
            left = null;
            right = null;
            parent = null;
            color = NodeColor.Red;
        }
    }

    private Node root; //handle on root node

    public RBTree() {
        root = null;
    }

    // Method will insert values into the tree
    // and in the method, will use helper methods
    // to adjust and fix with each new insert
    public void insert(int value) {
        Node newNode = new Node(value);
        root = insertRec(root, newNode);
        fixInsert(newNode);
    }

    // Method will delete desired value.
    // It will use helper method to find the node
    // and another method to help rearrange and
    // adjust color after deleting
    public boolean delete(int value) {
        Node node = findNode(root, value);
        if(node==null){
            return false;
        }
        deleteNode(node);
        return true;
    }

    // Method will use a helper method to find
    // the value (node) that is inputted
    public boolean find(int value){
        return findNode(root, value) != null;
    }


    /**
     * The following methods are helper methods
     * that are used for the methods above and also
     * in other helper methods too.
     * @param root
     * @param node
     * @return
     */

    // This helper method will help sort and put the tree
    // in order based off the values of the node and the root
    private Node insertRec(Node root, Node node) {
        if (root == null) {
            return node;
        }

        if (node.value < root.value) {
            root.left = insertRec(root.left, node);
            root.left.parent = root;
        } else if (node.value > root.value) {
            root.right = insertRec(root.right, node);
            root.right.parent = root;
        }

        return root;
    }

    // This method is used in the insert main function.
    // This will help insert the values in the correct order
    private void fixInsert(Node node) {
        Node parent = null;
        Node grandParent = null;

        while (node != root && node.color != NodeColor.Black && node.parent.color == NodeColor.Red) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;
                if (uncle != null && uncle.color == NodeColor.Red) {
                    grandParent.color = NodeColor.Red;
                    parent.color = NodeColor.Black;
                    uncle.color = NodeColor.Black;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateRight(grandParent);
                    NodeColor temp = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = temp;
                    node = parent;
                }
            } else {
                Node uncle = grandParent.left;
                if (uncle != null && uncle.color == NodeColor.Red) {
                    grandParent.color = NodeColor.Red;
                    parent.color = NodeColor.Black;
                    uncle.color = NodeColor.Black;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateLeft(grandParent);
                    NodeColor temp = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = temp;
                    node = parent;
                }
            }
        }
        root.color = NodeColor.Black;
    }

    // This method is a helper in the main delete method.
    // It will help readjust the tree after the deletion of a value
    // and help maintain the red-black tree properties.
    private void deleteNode(Node node){
        Node child = null;
        Node replacement = node;
        replacement.color = NodeColor.Red;
        NodeColor originalColor = replacement.color;

        if(node.left == null){
            child = node.right;
            transplant(node, node.right);
        } else if(node.right == null){
            child = node.left;
            transplant(node,node.left);
        } else {
            replacement = minimum(node.right);
            originalColor = replacement.color;
            child = replacement.right;

            if(replacement.parent == node){
                if(child != null){
                    child.parent = replacement;
                }
            } else {
                transplant(replacement, replacement.right);
                replacement.right = node.right;
                replacement.right.parent = replacement;
            }

            transplant(node, replacement);
            replacement.left = node.left;
            replacement.left.parent = replacement;
            replacement.color = NodeColor.Red;
        }
        if (originalColor == NodeColor.Black){
            fixDelete(child);
        }
    }

    // This method is used in the deleteNode() helper method.
    // It will replace one subtree with another which will help
    // for both deletion and rotations
    private void transplant(Node u, Node v){
        if(u.parent == null){
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if(v != null){
            v.parent = u.parent;
        }
    }

    // This helper method will find the nodes with the smallest values in a subtree
    // starting from a given node
    private Node minimum(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    // This helper method will restore the red-black tree properties
    // after the deletion if a black node was removed.
    // Checks for cases where the red-black properties are violated
    private void fixDelete(Node node){
        while (node != root && (node == null || node.color == NodeColor.Black)){
            if(node == node.parent.left){
                Node sibling = node.parent.right;
                if(sibling.color == NodeColor.Red){
                    sibling.color = NodeColor.Black;
                    node.parent.color = NodeColor.Red;
                    rotateLeft(node.parent);
                    sibling = node.parent.right;
                }
                if((sibling.left == null || sibling.left.color == NodeColor.Black) && (sibling.right == null || sibling.right.color == NodeColor.Black)){
                    sibling.color = NodeColor.Red;
                    node = node.parent;
                } else {
                    if(sibling.right == null || sibling.right.color == NodeColor.Black){
                        sibling.left.color = NodeColor.Black;
                        sibling.color = NodeColor.Red;
                        rotateRight(sibling);
                        sibling = node.parent.right;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = NodeColor.Black;
                    if(sibling.right != null){
                        sibling.right.color = NodeColor.Black;
                    }
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                Node sibling = node.parent.left;
                if(sibling.color == NodeColor.Red){
                    sibling.color = NodeColor.Black;
                    node.parent.color = NodeColor.Red;
                    rotateRight(node.parent);
                    sibling = node.parent.left;
                }
                if((sibling.left == null || sibling.left.color == NodeColor.Black) && (sibling.right == null || sibling.right.color == NodeColor.Black)){
                    sibling.color = NodeColor.Red;
                    node = node.parent;
                } else {
                    if(sibling.left == null || sibling.left.color == NodeColor.Black){
                        sibling.right.color = NodeColor.Black;
                        sibling.color = NodeColor.Red;
                        rotateLeft(sibling);
                        sibling = node.parent.left;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = NodeColor.Black;
                    if(sibling.left != null){
                        sibling.left.color = NodeColor.Black;
                    }
                    rotateRight(node.parent);
                    node = root;
                }
            }
        }
        if(node != null){
            node.color = NodeColor.Black;
        }
    }

    // this helper method will traverse down the
    // tree until it finds the inputted node
    private Node findNode(Node root, int value) {
        Node current = root;
        while (current != null) {
            if (value < current.value) {
                current = current.left;
            } else if (value > current.value) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    /**
     * This rotateLeft() and rotateRight() methods will help maintain
     * the red-black properties after inserting or deleting given nodes
     * @param node
     */
    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (node.left != null) {
            node.left.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        inOrderTraversal(root, sb);
        return sb.toString();
    }

    private void inOrderTraversal(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);
            sb.append(node.value).append(" ");
            inOrderTraversal(node.right, sb);
        }
    }
}
