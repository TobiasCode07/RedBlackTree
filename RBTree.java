import static java.lang.Math.max;
import static java.lang.Math.min;

public class RBTree {
    Node root;

    Node heightNode;
    void add(int key, int value){
        if (root == null){
            root = new Node(key, value);
            root.isBlack = true;
            heightNode = root;
        }
        else{
            Node currentNode = root;
            while(true) {
                if (key > currentNode.key) {
                    if (currentNode.rightChild == null){
                        currentNode.rightChild = new Node(key, value);
                        currentNode.rightChild.parent = currentNode;
                        currentNode = currentNode.rightChild;
                        break;
                    }
                    else{
                        currentNode = currentNode.rightChild;
                    }
                }
                else {
                    if (currentNode.leftChild == null){
                        currentNode.leftChild = new Node(key, value);
                        currentNode.leftChild.parent = currentNode;
                        currentNode = currentNode.leftChild;
                        break;
                    }
                    else{
                        currentNode = currentNode.leftChild;
                    }
                }
            }
            insertFixUp(currentNode);
        }
    }
    void insertFixUp(Node node){
        if (node != root) {
            if (node.parent != root) {
                while (node.parent.isBlack) {
                    if (node.parent == node.parent.parent.leftChild) {
                        Node y = node.parent.parent.rightChild; // wujek
                        if (!y.isBlack) { // case 1 - wujek czerwony
                            node.parent.isBlack = true;
                            y.isBlack = true;
                            node.parent.parent.isBlack = false;
                            node = node.parent.parent;
                        } else { // case 2 / 3
                            if (node == node.parent.rightChild) {
                                node = node.parent;
                                leftRotate(node);
                            }
                            node.parent.isBlack = true;
                            node.parent.parent.isBlack = false;
                            rightRotate(node.parent.parent);
                        }
                    } else {
                        Node y = node.parent.parent.leftChild; // wujek
                        if (!y.isBlack) { // case 1 - wujek czerwony
                            node.parent.isBlack = true;
                            y.isBlack = true;
                            node.parent.parent.isBlack = false;
                            node = node.parent.parent;
                        } else { // case 2 / 3
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
            }
        }
        root.isBlack = true;
        heightNode = root;
    }
    int get(int key){
        if (getNode(key) != null){
            return getNode(key).value;
        }
        else{
            return 0;
        }
    }
    void leftRotate(Node x){
        Node y = x.rightChild;
        x.rightChild = y.leftChild;
        if (y.leftChild != null){
            y.leftChild.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null){
            root = y;
            heightNode = root;
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
    void rightRotate(Node x){
        Node y = x.leftChild;
        x.leftChild = y.rightChild;
        if (y.rightChild != null){
            y.rightChild.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null){
            root = y;
            heightNode = root;
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

    int height(){
        if (heightNode.leftChild == null && heightNode.rightChild == null){
            return (1);
        }
        else if (heightNode.leftChild != null && heightNode.rightChild != null){
            Node x = heightNode;
            heightNode = heightNode.leftChild;
            int leftHeight = height();
            heightNode = x.rightChild;
            int rightHeight = height();
            return max(leftHeight, rightHeight) + 1;
        }
        else if (heightNode.leftChild != null){
            heightNode = heightNode.leftChild;
            return (height() + 1);
        }
        else{
            heightNode = heightNode.rightChild;
            return (height() + 1);
        }
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
    void transplant(Node u, Node v){
        if (u.parent == null){
            root = v;
            heightNode = root;
        }else if(u == u.parent.leftChild){
            u.parent.leftChild = v;
        }else{
            u.parent.rightChild = v;
        }
        if(v != null){
            v.parent = u.parent;
        }
    }

    Node minimumNode(Node node){
        while (node.leftChild != null){
            node = node.leftChild;
        }
        return (node);
    }

    void deleteFixup(Node node){
        Node w;
        while (node != node.parent.leftChild){
            if (node == node.parent.leftChild) {
                w = node.parent.rightChild;
                if (!w.isBlack) {
                    w.isBlack = true;
                    node.parent.isBlack = false;
                    leftRotate(node.parent);
                    w = node.parent.rightChild;
                }
                if (w.leftChild.isBlack && w.rightChild.isBlack){
                    w.isBlack = false;
                    node = node.parent;
                }
                else{
                    if (w.rightChild.isBlack){
                        w.leftChild.isBlack = true;
                        w.isBlack = false;
                        rightRotate(w);
                        w = node.parent.rightChild;
                    }else {
                        w.isBlack = node.parent.isBlack;
                        node.parent.isBlack = true;
                        w.rightChild.isBlack = true;
                        leftRotate(node.parent);
                        node = root;
                    }
                }
            }
            else{
                w = node.parent.leftChild;
                if (!w.isBlack) {
                    w.isBlack = true;
                    node.parent.isBlack = false;
                    rightRotate(node.parent);
                    w = node.parent.leftChild;
                }
                if (w.rightChild.isBlack && w.leftChild.isBlack){
                    w.isBlack = false;
                    node = node.parent;
                }
                else{
                    if (w.leftChild.isBlack){
                        w.rightChild.isBlack = true;
                        w.isBlack = false;
                        leftRotate(w);
                        w = node.parent.leftChild;
                    }else {
                        w.isBlack = node.parent.isBlack;
                        node.parent.isBlack = true;
                        w.leftChild.isBlack = true;
                        rightRotate(node.parent);
                        node = root;
                    }
                }
            }
        }
        node.isBlack = true;
    }
    int remove(int key){
        Node deletionNode = getNode(key);
        Node deletionNode2 = deletionNode;
        Node x;
        Boolean deletionNodeIsBlack = deletionNode2.isBlack;
        if (deletionNode.leftChild == null){
            x = deletionNode.rightChild;
            transplant(deletionNode, deletionNode.rightChild);
        }else if (deletionNode.rightChild == null){
            x = deletionNode.leftChild;
            transplant(deletionNode, deletionNode.leftChild);
        }else {
            deletionNode2 = minimumNode(deletionNode.rightChild);
            deletionNodeIsBlack = deletionNode2.isBlack;
            x = deletionNode2.rightChild;
            if(deletionNode2.parent == deletionNode){
                x.parent = deletionNode2;
            }else {
                transplant(deletionNode2, deletionNode2.rightChild);
                deletionNode2.rightChild = deletionNode.rightChild;
                deletionNode2.rightChild.parent = deletionNode2;
            }
            transplant(deletionNode, deletionNode2);
            deletionNode2.leftChild = deletionNode.leftChild;
            deletionNode2.leftChild.parent = deletionNode2;
            deletionNode2.isBlack = deletionNode.isBlack;
        }
        if(deletionNodeIsBlack){
            deleteFixup(x);
        }
        return (5);
    }
}


 