import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Filename:   Knapsack.java
 * Project:    Assignment 8 - More Dynamic Programming
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   03/30/22
 *
 * Dynammic programming algorithm to solve the Knapsack problem. Given a set of items where each
 * item has a value and a weight, and a knapsack with a weight capacity, determine the optimal set
 * of items the knapsack should take and print out its overall value.
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
     * Representation of an item to be potentially added to the knapsack
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
     * Parse stdin and create instances of knpasacks and items
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
            instances[numInstance].items.add(null); // add dummy item for matrix
            input.nextLine(); // Read the rest of the line to go to the next line

            // Get the weight and value of item and add it to list
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

    /**
     * Creates a new item object and adds it to the knapsack's list
     * 
     * @param weight the integer weight of the item
     * @param value the interger value of the item
     */
    private void addItem(int weight, int value) {
        Item item = new Item(weight, value);
        this.items.add(item);
    }

    /**
     * Calculates the optimal set of items that should be added to the knapsack considering its
     * weight capacity using a DP approach
     * 
     * @return the summed value of the optimal set of items
     */
    private int getMaxValue() {

        int i;
        for (i = 0; i <= this.numItems; i++) {
            for (int w = 0; w <= this.weightCapacity; w++) {
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

        return this.m[this.numItems][this.weightCapacity]; // return last entry
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