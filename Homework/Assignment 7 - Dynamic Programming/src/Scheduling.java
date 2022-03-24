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
public class Scheduling {

    private List<Job> jobs;

    /**
     * Constructor for Scheduling object
     * 
     * @param numJobs the number of jobs the priority queue should hold
     */
    public Scheduling(int numJobs) {
        jobs = new ArrayList<Job>();
    }

    /**
     * Representation of a job to be processed with a start time and end time 
     */
    private class Job {
        private int startTime;
        private int endTime;
        private long weight;

        private Job(int start, int end, long weight) {
            this.startTime = start;
            this.endTime = end;
            this.weight = weight;
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
     * Custom comparator to sort jobs. This one sorts by increasing start time. 
     */
    private class startTimeComparator implements Comparator<Job> {

        @Override
        public int compare(Scheduling.Job j1, Scheduling.Job j2) {
            if (j1.startTime > j2.startTime) {
                return 1;
            } else if (j1.startTime < j2.startTime) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Parse stdin and create instances with jobs
     */
    private static Scheduling[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        Scheduling instances[] = new Scheduling[numInstances];

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numJobs = input.nextInt(); // Get number of jobs per instance
            instances[numInstance] = new Scheduling(numJobs); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Each line represents a job
            while (numJobs > 0) {
                int start = input.nextInt();
                int end = input.nextInt();
                long weight = input.nextLong();
                
                instances[numInstance].addJob(start, end, weight);
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
     * @param weight the weight of the job
     */
    private void addJob(int startTime, int endTime, long weight) {
        Job j = new Job(startTime, endTime, weight);
        jobs.add(j);
    }

    /**
     * Checks if there exists a job in the schedule that conflicts with a potential new job
     * 
     * @param i the job to check
     * @param j job to check against
     * @return true if the jobs do not overlap
     */
    private Boolean checkOverlap(Job i, Job j) {
        if (i.endTime <= j.startTime || j.endTime <= i.startTime) {
            return true;
        }
        return false;
    }

    /**
     * Returns the latest job in terms of starting time that is compatible with a given job.
     * 
     * @param j the job to check against
     * @return the first compatible job from the list of jobs
     */
    private Job getLatestCompatibleJob(Job j) {
        for (Job k : this.jobs) {
            if (k == j) {
                continue;
            }
            if (checkOverlap(j, k)) {
                return k;
            }
        }

        return null;
    }
    /**
     * Creates compatible schedules for each job and finds the one with the highest weight
     * 
     * @return a set of jobs
     */
    private long findTotalWeight() {

        this.jobs.sort(new endTimeComparator()); // sort jobs by decreasing end times

        int numJobs = jobs.size();
        
        // base case
        if (numJobs == 0) {
            return 0;
        }

        long[] arr = new long[numJobs]; // stores weight of non-conflicting jobs from 0 to i
        arr[0] = this.jobs.get(0).weight;

        // loop through each job which are pre-sorted and populate array
        // We track the current max weight at each iteration
        for (int i = 1; i < numJobs; i++) {
            Job job = this.jobs.get(i); // Get the latest job first
            long weight = job.weight;
            Job compJob = getLatestCompatibleJob(job);

            // if jobs are compatible, then add the weights
            if (compJob != null) {
                weight += compJob.weight; 
            }

            arr[i] = Math.max(weight, arr[i - 1]);
        }

        return arr[numJobs - 1];
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