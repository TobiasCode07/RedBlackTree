public class Main {
    public static void main(String[] args) {
        RBTree rbTree = new RBTree();

        rbTree.add(1, 1);
        rbTree.add(2, 1);
        rbTree.add(3, 1);
        rbTree.add(4, 1);
        rbTree.add(5, 1);
        rbTree.add(6, 1);
        rbTree.add(7, 1);
        rbTree.add(8, 1);

        rbTree.getNode(1).showParameters();
        rbTree.getNode(2).showParameters();
        rbTree.getNode(3).showParameters();
        rbTree.getNode(4).showParameters();
        rbTree.getNode(5).showParameters();
        rbTree.getNode(6).showParameters();
        rbTree.getNode(7).showParameters();
        rbTree.getNode(8).showParameters();
        System.out.println("Root:");
        rbTree.root.showParameters();


        System.out.println("Height:");
        System.out.println(rbTree.height());

        rbTree.remove(4);

        System.out.println("Root:");
        rbTree.root.showParameters();

        rbTree.getNode(1).showParameters();
        rbTree.getNode(2).showParameters();
        rbTree.getNode(3).showParameters();
        rbTree.getNode(5).showParameters();
        rbTree.getNode(6).showParameters();
        rbTree.getNode(7).showParameters();
        rbTree.getNode(8).showParameters();

//        rbTree.add(10, 100);
//        rbTree.add(5, 50);
//        rbTree.add(15, 150);
//        rbTree.add(3, 30);
//        rbTree.add(7, 70);
//        rbTree.add(13, 130);
//        rbTree.add(17, 170);
//
//        System.out.println("Wysokość drzewa przed usuwaniem: " + rbTree.height());
//
//        // Usuwamy węzły
//        System.out.println("Usunięta wartość dla klucza 7: " + rbTree.remove(7));
//        System.out.println("Wysokość drzewa po usunięciu: " + rbTree.height());
//
//        System.out.println("Usunięta wartość dla klucza 17: " + rbTree.remove(17));
//        System.out.println("Wysokość drzewa po usunięciu: " + rbTree.height());
//
//        System.out.println("Usunięta wartość dla klucza 15: " + rbTree.remove(15));
//        System.out.println("Wysokość drzewa po usunięciu: " + rbTree.height());
    }
}
