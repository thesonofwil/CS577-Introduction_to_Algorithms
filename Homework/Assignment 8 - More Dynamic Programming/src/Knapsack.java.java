import java.util.ArrayList;
import java.util.Comparator;
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

    public Knapsack(int numItems, int weightCapacity) {
        this.numItems = numItems;
        this.weightCapacity = weightCapacity;
        this.items = new ArrayList<Item>(numItems);
    }

    /**
     * Representation of a job to be processed with a start time and end time 
     */
    private class Item {
        private int weight;
        private int value;

        private Item(int weight, int value) {
            tthis.weight = weight;
            this.value = value;
        }
    }

    /**
     * Custom comparator to sort jobs. This one sorts by decreasing end time. 
     */
    private class endTimeComparator implements Comparator<Job> {

        @Override
        public int compare(Scheduling.Job j1, Scheduling.Job j2) {
            if (j1.endTime < j2.endTime) {
                return 1;
            } else if (j1.endTime > j2.endTime) {
                return -1;
            }
            return 0;
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
            instances[numInstance] = new Knapsack(numJobs); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Each line represents a job
            while (numItems > 0) {
                int weight = input.nextInt();
                int value = input.nextInt();
                Item item = new Item(weight, value);
                
                instances[numInstance].items.add(item);
                numItems--;
            }
        }
        input.close();
        return instances;
    }

    public static void main(String[] args) {
        try {
            Scheduling[] instances = parse_input();
            for (Scheduling s : instances) {
                long maxWeight = s.findTotalWeight();
                System.out.println(maxWeight);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}