import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FF {
	
	private PriorityQueue<Integer> cache; // Cache to hold pages
    private List<Integer> requests; // Sequence of requests
	private HashMap<Integer, ArrayList<Integer>> indexMap; // k = page, v = list of indices
    private int cacheSize; // Size of cache
	
    /**
     * FF constructor
     * 
     * @param numPages the size of the cache
     * @param numRequests the number of integers in the sequence of requests
     */
	public FF(int numPages, int numRequests) {
		cache = new PriorityQueue<Integer>(numPages, new lastIndexComparator());
        cacheSize = numPages;
		requests = new ArrayList<Integer>(numRequests);
        indexMap = new HashMap<Integer, ArrayList<Integer>>();
    }
	
    /**
     * Custom comparator to pages according to what their next index is in the sequence.
     *  
     */
    private class lastIndexComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer a, Integer b) {
            ArrayList<Integer> indicesA = indexMap.get(a);
            ArrayList<Integer> indicesB = indexMap.get(b);

            // Case 1: int has no more occurrences
            if (indicesA.isEmpty()) {
                return -1;
            } else if (indicesB.isEmpty()) {
                return 1;
            }

            // Case 2: int will appear in the future
            int nextIndexA = indicesA.get(0);
            int nextIndexB = indicesB.get(0);

            if (nextIndexA < nextIndexB) {
                return 1;
            } else if (nextIndexA > nextIndexB) {
                return -1;
            }
            return 0;
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
            instances[numInstance] = new FF(numPages, numRequests); // Create array of instances
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Store sequence of requests and map each one to its index
            for (int i = 0; i < numRequests; i++) {
            	int request = input.nextInt();
                instances[numInstance].requests.add(request);

                // Add page request to index map and update with index as we read sequence
                if (instances[numInstance].indexMap.containsKey(request)) {
                    instances[numInstance].indexMap.get(request).add(i);
                } else {
                    instances[numInstance].indexMap.put(request, new ArrayList<Integer>());
                    instances[numInstance].indexMap.get(request).add(i);
                }
            }
        }
        input.close();
        return instances;
    }
	
    /**
     * Read sequence of integers into cache, evicting using a "Farthest in Future" policy.
     *
     * @param instance FF object containing sequence, cache, and indices
     * @return the number of page faults that occur
     */
    private int readPages(FF instance) {
        int pageFaults = 0;

        for (int page : instance.requests) {
            if (!instance.cache.contains(page)) {
                pageFaults++;

                // Need to evict 
                if (instance.cache.size() == instance.cacheSize) {
                    instance.cache.poll(); // Remove int with farthest index in future
                    instance.indexMap.get(page).remove(0); // Update index map
                    instance.cache.add(page);
                } else {
                    instance.indexMap.get(page).remove(0);
                    instance.cache.add(page);
                }
            }  else {
                instance.indexMap.get(page).remove(0); // update index map
                
                // update PQ with new indices
                instance.cache.remove(page);
                instance.cache.add(page);
            }
        }
        return pageFaults;
    }

	public static void main(String[] args) {
		FF[] instances = parse_input();
        for (FF f : instances) {
            System.out.println(f.readPages(f));
        }
	}
}