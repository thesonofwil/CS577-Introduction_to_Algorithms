import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FF {
	
	private PriorityQueue<Integer> cache;
	private HashMap<Integer, Integer> requests; // k = page, v = index
	
	public FF(int numPages, int numRequests) {
		cache = new PriorityQueue<Integer>(numPages); // ordered by farthest occurrence
		requests = new HashMap<Integer, Integer>(2 * numRequests);
	}
	
	/**
     * Representation of a job to be processed with a start time and end time 
     */
    private class Page {
        private int value; // the actual int
        private int farthestIndex; // farthest index in request
    	
        private Page(int value) {
            this.value = value;
        }
    }
	
	/**
     * Parse stdin and create cache instances
     */
    private static FF[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        FF instances[] = new FF[numInstances];

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numPages = input.nextInt(); // size of cache
            int numRequests = input.nextInt(); // total size of page requests
            instances[numInstance] = new FF(numPages, numRequests); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Store sequence of requests and map each one to its index
            for (int i = 0; i < numRequests; i++) {
            	int request = input.nextInt();
                instances[numInstance].requests.put(i, request);
            }
        }
        input.close();
        return instances;
    }
	
	public static void main(String[] args) {
		FF[] instances = parse_input();
		System.out.println("Hello");
	}
}
