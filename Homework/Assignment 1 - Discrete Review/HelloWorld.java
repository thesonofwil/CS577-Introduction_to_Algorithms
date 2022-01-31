import java.util.Scanner;
import java.util.ArrayList;

public class HelloWorld {
    public static void main(String [] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> strings = new ArrayList<String>();
    
        int n = input.nextInt(); // Get integer

        // Store string inputs
        while (n > 0) { 
            String s = input.nextLine();
            strings.add(s);
            n--;
        }

        // Print strings
        for (String i : strings) { 
            System.out.println("Hello, " + i + "!");
        }

        input.close();
    }
}
