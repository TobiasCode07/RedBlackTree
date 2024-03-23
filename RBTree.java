import static java.lang.Math.max;
import static java.lang.Math.min;

public class RBTree {
    Node root;

    public void add(int key, int value){
        Node y = null;

        Node currentNode = root;
        while(currentNode != null) {
            y = currentNode;
            if (key < currentNode.key) {
                currentNode = currentNode.leftChild;
            }
            else{
                currentNode = currentNode.rightChild;
            }
        }

        Node addedNode = new Node(key, value);
        addedNode.parent = y;

        if (y == null){
            root = addedNode;
        }
        else if (key < y.key){
            y.leftChild = addedNode;
        }
        else{
            y.rightChild = addedNode;
        }

//        addedNode.leftChild = null;
//        addedNode.rightChild = null;
        addedNode.isBlack = false;

        insertFixUp(addedNode);
    }
    private void insertFixUp(Node node) {
        Node y;

        while (node != root && !node.parent.isBlack) {
            if (node.parent == node.parent.parent.leftChild) {
                y = node.parent.parent.rightChild; // uncle

                // case 1 : uncle red
                if (y != null && !y.isBlack) {
                    node.parent.isBlack = true;
                    y.isBlack = true;
                    node.parent.parent.isBlack = false;
                    node = node.parent.parent;
                } else {
                    // case 2 / 3 : uncle black or not exists
                    if (node == node.parent.rightChild) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isBlack = true;
                    node.parent.parent.isBlack = false;
                    rightRotate(node.parent.parent);
                }
            } else {
                y = node.parent.parent.leftChild; // uncle

                // case 1 : uncle red
                if (y != null && !y.isBlack) {
                    node.parent.isBlack = true;
                    y.isBlack = true;
                    node.parent.parent.isBlack = false;
                    node = node.parent.parent;
                } else {
                    // case 2 / 3 : uncle black or not exists
                    if (node == node.parent.leftChild) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isBlack = true;
                    node.parent.parent.isBlack = false;
                    leftRotate(node.parent.parent);
                }
            }
        }

        root.isBlack = true;
    }
    public int get(int key){
        if (getNode(key) != null){
            return getNode(key).value;
        }
        else{
            return 0;
        }
    }
    private void leftRotate(Node x){
        Node y = x.rightChild;
        x.rightChild = y.leftChild;
        if (y.leftChild != null){
            y.leftChild.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null){
            root = y;
        }
        else if(x == x.parent.leftChild){
            x.parent.leftChild = y;
        }
        else{
            x.parent.rightChild = y;
        }
        y.leftChild = x;
        x.parent = y;
    }
    private void rightRotate(Node x){
        Node y = x.leftChild;
        x.leftChild = y.rightChild;
        if (y.rightChild != null){
            y.rightChild.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null){
            root = y;
        }
        else if(x == x.parent.rightChild){
            x.parent.rightChild = y;
        }
        else{
            x.parent.leftChild = y;
        }
        y.rightChild = x;
        x.parent = y;
    }

    private int heightHelp(Node currentNode){
        if (currentNode.leftChild == null && currentNode.rightChild == null){
            return (1);
        }
        else if (currentNode.leftChild != null && currentNode.rightChild != null){
            return max(heightHelp(currentNode.leftChild), heightHelp(currentNode.rightChild)) + 1;
        }
        else if (currentNode.leftChild != null){
            return (heightHelp(currentNode.leftChild) + 1);
        }
        else{
            return (heightHelp(currentNode.rightChild) + 1);
        }
    }

    public int height(){
        return (heightHelp(root));
    }


    Node getNode(int key){
        Node currentNode = root;
        while(true){
            if (currentNode == null){
                System.out.println("[RBTree] Podany klucz nie istnieje");
                return null;
            }
            if (currentNode.key == key){
                return currentNode;
            }
            if(key > currentNode.key){
                currentNode = currentNode.rightChild;
            }
            else{
                currentNode = currentNode.leftChild;
            }
        }
    }

    //u = to co usuwamy | v = jego dziecko
    private void transplant(Node u, Node v){
        if (u.parent == null){
            root = v;
        }else if(u == u.parent.leftChild){
            u.parent.leftChild = v;
        }else{
            u.parent.rightChild = v;
        }
        if (v != null){
            v.parent = u.parent;
        }
    }

    private Node minimumNode(Node node){
        while (node.leftChild != null){
            node = node.leftChild;
        }
        return (node);
    }

    private void deleteFixup(Node node) {
        Node w;
        while (node != null && node != root && node.isBlack) {
            if (node == node.parent.leftChild) {
                w = node.parent.rightChild;

                // Case 1: Brother is red
                if (w != null && !w.isBlack) {
                    w.isBlack = true;
                    node.parent.isBlack = false;
                    leftRotate(node.parent);
                    w = node.parent.rightChild;
                }

                // Case 2: Brother and its children are black
                if (w.leftChild.isBlack && w.rightChild.isBlack) {
                    w.isBlack = false;
                    node = node.parent;
                } else {
                    // Case 3: Brother's left child is red and right child is black
                    if (w.rightChild.isBlack) {
                        w.leftChild.isBlack = true;
                        w.isBlack = false;
                        rightRotate(w);
                        w = node.parent.rightChild;
                    }

                    // Case 4: Brother's right child is red
                    w.isBlack = node.parent.isBlack;
                    node.parent.isBlack = true;
                    w.rightChild.isBlack = true;
                    leftRotate(node.parent);
                    node = root; // Update 'node' to root
                }
            } else {
                // Analogous for the case where the node is the right child
                w = node.parent.leftChild;

                // Case 1: Brother is red
                if (!w.isBlack) {
                    w.isBlack = true;
                    node.parent.isBlack = false;
                    rightRotate(node.parent);
                    w = node.parent.leftChild;
                }

                // Case 2: Brother and its children are black
                if (w.rightChild.isBlack && w.leftChild.isBlack) {
                    w.isBlack = false;
                    node = node.parent;
                } else {
                    // Case 3: Brother's right child is red and left child is black
                    if ( w.leftChild.isBlack) {
                        w.rightChild.isBlack = true;
                        w.isBlack = false;
                        leftRotate(w);
                        w = node.parent.leftChild;
                    }

                    // Case 4: Brother's left child is red
                    w.isBlack = node.parent.isBlack;
                    node.parent.isBlack = true;
                    w.leftChild.isBlack = true;
                    rightRotate(node.parent);
                    node = root; // Update 'node' to root
                }
            }
        }
        if (node != null){
            node.isBlack = true;
        }
    }


    public int remove(int key) {
        Node z = getNode(key);
        Node y = z;
        int deletedValue = y.value;

        if (z == null) {
            System.out.println("[RBTree] Węzeł do usunięcia nie istnieje.");
            return -1; // lub jakiś inny kod błędu, jeśli to odpowiednie
        }

        Node x;
        boolean yOrignalColor = y.isBlack;
        if(z.leftChild == null) {
            x = z.rightChild;
            transplant(z, z.rightChild);
        }
        else if(z.rightChild == null) {
            x = z.leftChild;
            transplant(z, z.leftChild);
        }
        else {
            y = minimumNode(z.rightChild);
            yOrignalColor = y.isBlack;
            x = y.rightChild;
            if(y.parent == z) {
                x.parent = z;
            }
            else {
                transplant(y, y.rightChild);
                y.rightChild = z.rightChild;
                y.rightChild.parent = y;
            }
            transplant(z, y);
            y.leftChild = z.leftChild;
            y.leftChild.parent = y;
            y.isBlack = z.isBlack;
        }
        if(yOrignalColor)
            deleteFixup(x);

        return deletedValue;
    }



}