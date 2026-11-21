
import java.util.Scanner;

public class RecursionTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a (number of subproblems): ");
        int a = sc.nextInt();

        System.out.print("Enter b (division factor): ");
        int b = sc.nextInt();

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.print("Enter c for f(n) = c * n^k: ");
        double c = sc.nextDouble();

        System.out.print("Enter k: ");
        double k = sc.nextDouble();

        if (a < 1 || b < 2 || n < 1 || c < 0 || k < 0) {
            System.out.println("Invalid input.");
            return;
        }

        int temp = n;
        int levels = 0;

        while (temp > 1) {
            if (temp % b != 0) {
                System.out.println("n must be an exact power of b.");
                return;
            }
            temp /= b;
            levels++;
        }

        System.out.println("\nRECURRENCE RELATION");
        System.out.println("T(n) = aT(n/b) + cn^k");
        System.out.println("T(1) = 1");

        System.out.println("\nSUBSTITUTING VALUES");
        System.out.println("T(n) = " + a + "T(n/" + b + ") + " + c + "n^" + k);

        System.out.println("\nRECURSION TREE");

        double total = 0;
        double nodes = 1;
        int size = n;

        System.out.println("\nLevel\tSubproblems\tSize\t\tCost");

        for (int i = 0; i < levels; i++) {
            double cost = nodes * c * Math.pow(size, k);

            System.out.printf("%d\t%.0f\t\t%d\t\t%.4f%n",
                    i, nodes, size, cost);

            total += cost;
            nodes *= a;
            size /= b;
        }

        double leafCost = nodes;

        System.out.printf("%d\t%.0f\t\t1\t\t%.4f (Leaf Cost)%n",
                levels, nodes, leafCost);

        total += leafCost;

        System.out.println("\nTOTAL COST");
        System.out.printf("Total Cost = %.4f%n", total);

        double logBA = Math.log(a) / Math.log(b);

        System.out.println("\nMASTER THEOREM COMPARISON");
        System.out.printf("log_b(a) = log_%d(%d) = %.4f%n", b, a, logBA);
        System.out.println("k = " + k);

        if (Math.abs(logBA - k) < 0.000001) {
            System.out.println("log_b(a) = k");
            System.out.println("Time Complexity = Theta(n^" + k + " log n)");
        } else if (logBA > k) {
            System.out.println("log_b(a) > k");
            System.out.println("Time Complexity = Theta(n^log_b(a))");
        } else {
            System.out.println("log_b(a) < k");
            System.out.println("Time Complexity = Theta(n^" + k + ")");
        }

        sc.close();
    }
}


