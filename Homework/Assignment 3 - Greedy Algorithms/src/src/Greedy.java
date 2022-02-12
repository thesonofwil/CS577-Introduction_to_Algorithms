import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Filename:   Greedy.java
 * Project:    Assignment 3 - Greedy Algorithms
 * Authors:    Wilson Tjoeng
 * Course:	   CS577.002
 * Due:		   02/16/22
 *
 * Greedy algorithm for interval scheduling. Given a list of schedules, the algorithm will 
 * determine an optimal schedule and output the max possible number of intervals 
 * that can be scheduled.
 */
public class Greedy {

    private PriorityQueue<Job> jobs;

    /**
     * Constructor for Greedy object
     * 
     * @param numJobs the number of jobs the priority queue should hold
     */
    public Greedy(int numJobs) {
        jobs = new PriorityQueue<Job>(numJobs, new JobComparator());
    }

    private class Job {
        private int startTime;
        private int endTime;

        private Job(int start, int end) {
            this.startTime = start;
            this.endTime = end;
        }
    }

    private class JobComparator implements Comparator<Job> {

        @Override
        public int compare(Greedy.Job j1, Greedy.Job j2) {
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
    private static Greedy[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        Greedy instances[] = new Greedy[numInstances];

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numJobs = input.nextInt(); // Get number of jobs per instance
            instances[numInstance] = new Greedy(numJobs); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Each line represents a vertex and its edges
            while (numJobs > 0) {
                int start = input.nextInt();
                int end = input.nextInt();
                
                instances[numInstance].addJob(start, end);
                numJobs--;
            }
        }
        input.close();
        return instances;
    }

    /**
     * Add a job to the list of jobs
     * 
     * @param startTime starting time of job
     * @param endTime ending time of job
     */
    private void addJob(int startTime, int endTime) {
        Job j = new Job(startTime, endTime);
        jobs.add(j);
    }

    private ArrayList<Job> createSchedule(Greedy g) {
        return null;
    }

    public static void main(String[] args) {
        Greedy[] instances = parse_input();
        for (Greedy g : instances) {
            System.out.println("Test");
            System.out.println(g.jobs.size());
        }
    }
}