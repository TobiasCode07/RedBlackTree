public class Node {
    int key;
    int value;
    Node leftChild = null;
    Node rightChild = null;
    Node parent;

    boolean isBlack = false;

    Node(int key, int value){
        this.key = key;
        this.value = value;
    }

    public void showParameters(){
        System.out.println("[Node] Key: " + key);
        System.out.println("[Node] Value: " + value);
        System.out.println("[Node] Parent (key): " + (parent == null ? null : parent.key));
        System.out.println("[Node] isBlack: " + isBlack);
        System.out.println("[Node] rightChild (key): " + (rightChild == null ? null : rightChild.key));
        System.out.println("[Node] leftChild (key): " + (leftChild == null ? null : leftChild.key));
    }
}