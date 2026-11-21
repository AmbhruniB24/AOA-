import java.util.Scanner;

class BTreeNode {
    int[] keys;
    BTreeNode[] child;
    int count;
    boolean leaf;

    BTreeNode(int t, boolean leaf) {
        keys = new int[2 * t - 1];
        child = new BTreeNode[2 * t];
        count = 0;
        this.leaf = leaf;
    }
}

class BTree {
    private BTreeNode root;
    private int t;

    BTree(int degree) {
        t = degree;
        root = new BTreeNode(t, true);
    }

    void insert(int key) {
        if (search(key)) {
            System.out.println("Duplicate key ignored.");
            return;
        }

        BTreeNode r = root;
        if (r.count == 2 * t - 1) {
            BTreeNode s = new BTreeNode(t, false);
            s.child[0] = r;
            splitChild(s, 0);
            root = s;
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.count - 1;

        if (node.leaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.count++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.child[i].count == 2 * t - 1) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.child[i], key);
        }
    }

    private void splitChild(BTreeNode parent, int index) {
        BTreeNode full = parent.child[index];
        BTreeNode right = new BTreeNode(t, full.leaf);
        right.count = t - 1;

        for (int j = 0; j < t - 1; j++) {
            right.keys[j] = full.keys[j + t];
        }

        if (!full.leaf) {
            for (int j = 0; j < t; j++) {
                right.child[j] = full.child[j + t];
            }
        }

        full.count = t - 1;

        for (int j = parent.count; j >= index + 1; j--) {
            parent.child[j + 1] = parent.child[j];
        }
        parent.child[index + 1] = right;

        for (int j = parent.count - 1; j >= index; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[index] = full.keys[t - 1];
        parent.count++;
    }

    boolean search(int key) {
        return searchNode(root, key);
    }

    private boolean searchNode(BTreeNode node, int key) {
        int i = 0;
        while (i < node.count && key > node.keys[i]) {
            i++;
        }
        if (i < node.count && key == node.keys[i]) {
            return true;
        }
        if (node.leaf) {
            return false;
        }
        return searchNode(node.child[i], key);
    }

    void display() {
        displayNode(root, 0);
    }

    private void displayNode(BTreeNode node, int level) {
        System.out.print("Level " + level + ": ");
        for (int i = 0; i < node.count; i++) {
            System.out.print(node.keys[i] + " ");
        }
        System.out.println();

        if (!node.leaf) {
            for (int i = 0; i <= node.count; i++) {
                displayNode(node.child[i], level + 1);
            }
        }
    }
}

public class BTreeProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter minimum degree (at least 2): ");
        int t = sc.nextInt();

        if (t < 2) {
            System.out.println("Minimum degree must be at least 2.");
            return;
        }

        BTree tree = new BTree(t);
        int choice;

        do {
            System.out.println("\n1. Insert\n2. Search\n3. Display\n0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter key: ");
                tree.insert(sc.nextInt());
            } else if (choice == 2) {
                System.out.print("Enter key to search: ");
                System.out.println(tree.search(sc.nextInt()) ? "Key found." : "Key not found.");
            } else if (choice == 3) {
                tree.display();
            }
        } while (choice != 0);

        sc.close();
    }
}