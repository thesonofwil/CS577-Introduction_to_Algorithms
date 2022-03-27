import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Filename:   Scheduling.java
 * Project:    Assignment 7 - Dynamic Programming
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   03/24/22
 *
 * Scheduling algorithm for weighted interval scheduling. Given a list of schedules, the
 * algorithm will determine an optimal schedule and output the total weight of its optimal
 * schedule.
 *
 * BUGS: program doesn't appear to accurately work when there are lots of inputs.
 */
public class Knapsack {

    private int numItems;
    private int weightCapacity;
    private List<Item> items;
    private int[][] m; // memoized matrix. default entry is 0

    public Knapsack(int numItems, int weightCapacity) {
        this.numItems = numItems;
        this.weightCapacity = weightCapacity;
        this.items = new ArrayList<Item>(numItems);
        this.m = new int[numItems + 1][weightCapacity + 1];
    }

    /**
     * Representation of a job to be processed with a start time and end time
     */
    private class Item {
        private int weight;
        private int value;

        private Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    /**
     * Parse stdin and create instances with jobs
     */
    private static Knapsack[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        Knapsack instances[] = new Knapsack[numInstances];

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numItems = input.nextInt(); // Get number of items per intance
            int weightCapacity = input.nextInt();
            instances[numInstance] = new Knapsack(numItems, weightCapacity); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line

            // Each line represents a job
            while (numItems > 0) {
                int weight = input.nextInt();
                int value = input.nextInt();

                instances[numInstance].addItem(weight, value);
                numItems--;
            }
        }
        input.close();
        return instances;
    }

    private void addItem(int weight, int value) {
        Item item = new Item(weight, value);
        this.items.add(item);
    }

    private int getMaxValue() {

        int i;
        for (i = 0; i <= this.numItems; i++) {
            for (int w = 0; i <= this.weightCapacity; w++) {
                // First row and column are all zeros, which is intialized by default
                if (i == 0 || w == 0) {
                    continue;
                }

                Item currItem = this.items.get(i);
                // weight exceeds capacity of consideration
                if (currItem.weight > w) {
                    this.m[i][w] = m[i - 1][w]; // copy value from above

                    // Bellman equation: choose the max value between with the item and without
                } else if (currItem.value + this.m[i - 1][w - currItem.weight] > this.m[i - 1][w]) {
                    m[i][w] = currItem.value + this.m[i - 1][w - currItem.weight];
                } else {
                    m[i][w] = m[i - 1][w];
                }
            }
        }

        return this.m[this.numItems][this.weightCapacity];
    }

    public static void main(String[] args) {
        try {
            Knapsack[] instances = parse_input();
            for (Knapsack k : instances) {
                int maxValue = k.getMaxValue();
                System.out.println(maxValue);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}