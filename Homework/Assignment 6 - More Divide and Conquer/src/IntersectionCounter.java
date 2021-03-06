import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Given a set of line segments whose ends are at y = 0 and y = 1, this program counts the number
 * of times the lines intersect each other. We use a divide and conquer approach, first sorting
 * the lines based on their x coordinates, then recursively counting the number of inversions
 * the resulting set has on their y coordinates.
 * 
 * BUGS: since we're counting inversions again, we face the same issues from the last assignment
 * where the output is not accurate. Tracing through each step, I see how the program underreports
 * inversions, but I am unsure how to fix it.
 */
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
     * Custom comparator to sort lines by increasing p points.
     */
    private static class pComparator implements Comparator<Line> {

        @Override
        public int compare(IntersectionCounter.Line l1, IntersectionCounter.Line l2) {
            if (l1.pPoint > l2.pPoint) {
                return 1;
            } else if (l1.pPoint < l2.pPoint) {
                return -1;
            }
            return 0;
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
                int qPoint = instances[numInstance].qSet.get(p);
                Line line = new Line(pPoint, qPoint);
                instances[numInstance].lines.add(line);
            }
        }

        input.close();
        return instances;
    }

    /**
     * Counts the number of inversions between two lists, A and B
     * 
     * @param A the first list of lines
     * @param B the second list of lines
     * @return the number of inversions detected
     */
    private static int mergeAndCount(List<Line> A, List<Line> B) {
        int count = 0;

        while (!A.isEmpty() && !B.isEmpty()) {
            if (A.get(0).qPoint <= B.get(0).qPoint) {
                A.remove(0); // Not an inversion
            } else {
                B.remove(0);
                count = count + A.size(); // inversion with the rest of A
            }
        }

        return count;
    }

    /**
     * Divide and conquer function which recursively splits an array of lines in halves and counts
     * the number of inversions between those two halves
     * 
     * @param arr the initial array of lines whose p points are already sorted
     * @return the number of inversions in q, which yields the number of intersections
     */
    private static int divide(List<Line> arr) {
        // Base Case: no inversions if list has one or no elements
        if (arr.size() <= 1) {
            return 0;
        }

        // Split arr into two halves
        int k = (arr.size() + 1) / 2;
    
        List<Line> A = new ArrayList<Line>(); // left half
        List<Line> B = new ArrayList<Line>(); // right half
        
        for (int i = 0; i < k; i++) {
            A.add(arr.get(i));
        }
        for (int j = k; j < arr.size(); j++) {
            B.add(arr.get(j));
        }

        // Recursively split and count inversions in arrays
        int countA = divide(A);
        int countB = divide(B);
        int count = mergeAndCount(A, B);
    
        return countA + countB + count;
    }
	
    public static void main(String[] args) {
		IntersectionCounter[] instances = parse_input();
        for (IntersectionCounter k : instances) {
            //sort(k.lines); // sort p by increasing order
            Collections.sort(k.lines, new pComparator());
            int count = divide(k.lines);
            System.out.println(count);
        }
	}
}