
import java.util.Scanner;

public class MasterMethod {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a: ");
        double a = sc.nextDouble();

        System.out.print("Enter b: ");
        double b = sc.nextDouble();

        System.out.print("Enter k for f(n) = n^k: ");
        double k = sc.nextDouble();

        if (a < 1 || b <= 1 || k < 0) {
            System.out.println("Invalid input.");
            return;
        }

        double logBA = Math.log(a) / Math.log(b);
        double epsilon = 0.000001;

        System.out.println("\nRECURRENCE RELATION");
        System.out.println("T(n) = aT(n/b) + f(n)");
        System.out.println("f(n) = n^k");

        System.out.println("\nSUBSTITUTING VALUES");
        System.out.println("T(n) = " + a + "T(n/" + b + ") + n^" + k);

        System.out.println("\nMASTER THEOREM");

        System.out.println("log_b(a) = log_" + b + "(" + a + ")");
        System.out.printf("log_b(a) = %.4f%n", logBA);

        System.out.println("k = " + k);

        System.out.println("\nCASE ANALYSIS");

        if (Math.abs(logBA - k) < epsilon) {
            System.out.println("log_b(a) = k");
            System.out.println("Case 2 applies.");

            System.out.println("T(n) = Theta(n^k log n)");
            System.out.println("T(n) = Theta(n^" + k + " log n)");

            if (Math.abs(k - 1) < epsilon) {
                System.out.println("Final Answer = Theta(n log n)");
            }

        } else if (logBA > k) {
            System.out.println("log_b(a) > k");
            System.out.println("Case 1 applies.");
            System.out.println("T(n) = Theta(n^log_b(a))");
            System.out.printf("Final Answer = Theta(n^%.4f)%n", logBA);

        } else {
            System.out.println("log_b(a) < k");

            double ratio = a / Math.pow(b, k);

            if (ratio < 1) {
                System.out.println("Case 3 applies.");
                System.out.println("Regularity condition is satisfied.");
                System.out.println("T(n) = Theta(n^k)");
                System.out.println("Final Answer = Theta(n^" + k + ")");
            } else {
                System.out.println("Regularity condition is not satisfied.");
                System.out.println("Standard Case 3 cannot be applied.");
            }
        }

        sc.close();
    }
}
