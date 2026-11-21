import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class BPlusNode {
    boolean leaf;
    List<Integer> keys = new ArrayList<>();
    List<BPlusNode> children = new ArrayList<>();
    BPlusNode next;

    BPlusNode(boolean leaf) {
        this.leaf = leaf;
    }
}

class BPlusTree {
    private BPlusNode root = new BPlusNode(true);
    private int order;

    BPlusTree(int order) {
        this.order = order;
    }

    void insert(int key) {
        if (search(key)) {
            System.out.println("Duplicate key ignored.");
            return;
        }

        BPlusNode leaf = findLeaf(key);
        leaf.keys.add(key);
        Collections.sort(leaf.keys);

        if (leaf.keys.size() == order) {
            splitLeaf(leaf);
        }
    }

    private BPlusNode findLeaf(int key) {
        BPlusNode node = root;
        while (!node.leaf) {
            int i = 0;
            while (i < node.keys.size() && key >= node.keys.get(i)) {
                i++;
            }
            node = node.children.get(i);
        }
        return node;
    }

    private void splitLeaf(BPlusNode leaf) {
        BPlusNode right = new BPlusNode(true);
        int split = (leaf.keys.size() + 1) / 2;

        right.keys.addAll(leaf.keys.subList(split, leaf.keys.size()));
        leaf.keys.subList(split, leaf.keys.size()).clear();

        right.next = leaf.next;
        leaf.next = right;

        int separator = right.keys.get(0);

        if (leaf == root) {
            BPlusNode newRoot = new BPlusNode(false);
            newRoot.keys.add(separator);
            newRoot.children.add(leaf);
            newRoot.children.add(right);
            root = newRoot;
        } else {
            insertIntoParent(root, leaf, separator, right);
        }
    }

    private boolean insertIntoParent(BPlusNode current, BPlusNode left, int key, BPlusNode right) {
        if (current.leaf) {
            return false;
        }

        int childIndex = current.children.indexOf(left);
        if (childIndex >= 0) {
            current.keys.add(childIndex, key);
            current.children.add(childIndex + 1, right);

            if (current.children.size() > order) {
                splitInternal(current);
            }
            return true;
        }

        for (BPlusNode child : current.children) {
            if (!child.leaf && insertIntoParent(child, left, key, right)) {
                return true;
            }
        }
        return false;
    }

    private void splitInternal(BPlusNode node) {
        int middle = node.keys.size() / 2;
        int promoted = node.keys.get(middle);

        BPlusNode right = new BPlusNode(false);
        right.keys.addAll(node.keys.subList(middle + 1, node.keys.size()));
        right.children.addAll(node.children.subList(middle + 1, node.children.size()));

        node.keys.subList(middle, node.keys.size()).clear();
        node.children.subList(middle + 1, node.children.size()).clear();

        if (node == root) {
            BPlusNode newRoot = new BPlusNode(false);
            newRoot.keys.add(promoted);
            newRoot.children.add(node);
            newRoot.children.add(right);
            root = newRoot;
        } else {
            insertIntoParent(root, node, promoted, right);
        }
    }

    boolean search(int key) {
        BPlusNode leaf = findLeaf(key);
        return leaf.keys.contains(key);
    }

    void display() {
        BPlusNode node = root;
        while (!node.leaf) {
            node = node.children.get(0);
        }

        System.out.println("Leaf-level keys:");
        while (node != null) {
            System.out.print(node.keys + " ");
            node = node.next;
        }
        System.out.println();
    }
}

public class BPlusTreeProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter order of B+ Tree (minimum 3): ");
        int order = sc.nextInt();

        if (order < 3) {
            System.out.println("Order must be at least 3.");
            return;
        }

        BPlusTree tree = new BPlusTree(order);
        int choice;

        do {
            System.out.println("\n1. Insert\n2. Search\n3. Display leaf level\n0. Exit");
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