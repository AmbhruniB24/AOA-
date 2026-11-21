import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Job {
    String id;
    int deadline;
    int profit;

    Job(String id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class JobSequencing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of jobs: ");
        int n = sc.nextInt();
        Job[] jobs = new Job[n];
        int maxDeadline = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter job ID, deadline and profit: ");
            String id = sc.next();
            int deadline = sc.nextInt();
            int profit = sc.nextInt();

            if (deadline < 1 || profit < 0) {
                System.out.println("Invalid deadline or profit.");
                return;
            }

            jobs[i] = new Job(id, deadline, profit);
            maxDeadline = Math.max(maxDeadline, deadline);
        }

        Arrays.sort(jobs, Comparator.comparingInt((Job job) -> job.profit).reversed());

        String[] slots = new String[maxDeadline];
        int totalProfit = 0;
        int count = 0;

        for (Job job : jobs) {
            for (int j = Math.min(job.deadline, maxDeadline) - 1; j >= 0; j--) {
                if (slots[j] == null) {
                    slots[j] = job.id;
                    totalProfit += job.profit;
                    count++;
                    break;
                }
            }
        }

        System.out.println("Scheduled jobs:");
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null) {
                System.out.println("Slot " + (i + 1) + ": " + slots[i]);
            }
        }

        System.out.println("Number of jobs selected = " + count);
        System.out.println("Maximum profit = " + totalProfit);
        sc.close();
    }
}