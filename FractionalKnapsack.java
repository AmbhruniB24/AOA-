import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Item {
    double weight;
    double profit;
    double ratio;

    Item(double weight, double profit) {
        this.weight = weight;
        this.profit = profit;
        this.ratio = profit / weight;
    }
}

public class FractionalKnapsack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight and profit of item " + (i + 1) + ": ");
            double weight = sc.nextDouble();
            double profit = sc.nextDouble();

            if (weight <= 0 || profit < 0) {
                System.out.println("Invalid weight or profit.");
                return;
            }
            items[i] = new Item(weight, profit);
        }

        System.out.print("Enter knapsack capacity: ");
        double capacity = sc.nextDouble();

        Arrays.sort(items, Comparator.comparingDouble((Item item) -> item.ratio).reversed());

        double totalProfit = 0;

        for (Item item : items) {
            if (capacity <= 0) {
                break;
            }

            if (item.weight <= capacity) {
                capacity -= item.weight;
                totalProfit += item.profit;
                System.out.printf("Take 100%% of item: weight %.2f, profit %.2f%n", item.weight, item.profit);
            } else {
                double fraction = capacity / item.weight;
                totalProfit += item.profit * fraction;
                System.out.printf("Take %.2f%% of item: weight %.2f, added profit %.2f%n",
                        fraction * 100, item.weight, item.profit * fraction);
                capacity = 0;
            }
        }

        System.out.printf("Maximum profit = %.2f%n", totalProfit);
        sc.close();
    }
}