public class Node {
    int key;
    int value;
    Node leftChild;
    Node rightChild;
    Node parent;

    boolean isBlack = false;

    Node(int key, int value){
        this.key = key;
        this.value = value;
    }
}