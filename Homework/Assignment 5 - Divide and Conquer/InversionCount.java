import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InversionCount {
	
    private List<Integer> arr;
	
    /**
     * Constructor for InversionCount instance
     * 
     * @param size the number of elements the list will hold
     */
	public InversionCount(int size) {
        arr = new ArrayList<Integer>(size);
    }
    
	/**
     * Parse stdin and create instances of InversionCount
     */
    private static InversionCount[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        InversionCount instances[] = new InversionCount[numInstances]; // Create array of instances

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numInts = input.nextInt(); // total size of array
            instances[numInstance] = new InversionCount(numInts); // Create instance 
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Read elements and push to array
            while (numInts > 0) {
                int num = input.nextInt();
                instances[numInstance].arr.add(num);
                numInts--;
            }
        }

        input.close();
        return instances;
    }

    /**
     * Counts the number of inversions between two lists, A and B
     * 
     * @param A the first list of ints
     * @param B the second list of ints
     * @return the number of inversions detected
     */
    private static int mergeAndCount(List<Integer> A, List<Integer> B) {
        int count = 0;

        while (!A.isEmpty() && !B.isEmpty()) {
            if (A.get(0) <= B.get(0)) {
                A.remove(0); // Not an inversion
            } else {
                B.remove(0);
                count = count + A.size(); // inversion with the rest of A
            }
        }

        return count;
    }

    /**
     * Divide and conquer function which recursively splits an array of ints in halves and counts
     * the number of inversions between those two halves
     * 
     * @param arr the initial array
     * @return the number of inversions
     */
    private static int sortAndCount(List<Integer> arr) {
        // Base Case: no inversions if list has one or no elements
        if (arr.size() <= 1) {
            return 0;
        }

        // Split arr into two halves
        int k = (arr.size() + 1) / 2;
    
        List<Integer> A = new ArrayList<Integer>();
        List<Integer> B = new ArrayList<Integer>();
        
        for (int i = 0; i < k; i++) {
            A.add(arr.get(i));
        }
        for (int j = k; j < arr.size(); j++) {
            B.add(arr.get(j));
        }

        // Recursively split and count inversions in arrays
        int countA = sortAndCount(A);
        int countB = sortAndCount(B);
        int count = mergeAndCount(A, B);
    
        return countA + countB + count;
    }
	
    public static void main(String[] args) {
		InversionCount[] instances = parse_input();
        for (InversionCount k : instances) {
            int count = sortAndCount(k.arr);
            System.out.println(count);
        }
	}
}