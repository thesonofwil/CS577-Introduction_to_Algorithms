import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IntersectionCounter {
	
    private List<Integer> pSet; // points that lie on y = 0
    private List<Integer> qSet; // points that lie on y = 1
    private List<Line> lines; // connected lines
	
    /**
     * Constructor for IntersectionCounter instance
     * 
     * @param size the number of elements the list will hold
     */
	public IntersectionCounter(int size) {
        pSet = new ArrayList<Integer>(size);
        qSet = new ArrayList<Integer>(size);
        lines = new ArrayList<Line>(size);
    }

    private static class Line {
        int pPoint; // point on y = 0
        int qPoint; // point on y = 1

        Line(int p, int q) {
            pPoint = p;
            qPoint = q;
        }
    }
    
	/**
     * Parse stdin and create instances of IntersectionCounter
     */
    private static IntersectionCounter[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        IntersectionCounter instances[] = new IntersectionCounter[numInstances]; // Create array of instances

        // Keep looping according to the number of instances
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numPoints = input.nextInt(); // total size of array
            instances[numInstance] = new IntersectionCounter(numPoints); // Create instance 
            input.nextLine(); // Read the rest of the line to go to the next line
            
            // Get set of q points
            for (int q = 0; q < numPoints; q++) {
                int qPoint = input.nextInt();
                instances[numInstance].qSet.add(qPoint);
            }
            
            // Get set of p points
            for (int p = 0; p < numPoints; p++) {
                int pPoint = input.nextInt();
                instances[numInstance].pSet.add(pPoint);
                
                // Create the line and store it
                // int qPoint = instances[numInstance].qSet.get(p);
                // Line line = new Line(pPoint, qPoint);
                // instances[numInstance].lines.add(line);
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
		IntersectionCounter[] instances = parse_input();
        for (IntersectionCounter k : instances) {
            int count = sortAndCount(k.arr);
            System.out.println(count);
        }
	}
}