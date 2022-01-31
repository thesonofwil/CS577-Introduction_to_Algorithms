import java.util.Scanner;

public class HelloWorld {
    public static void main(String [] args) {
        Scanner input = new Scanner(System.in);
        String[] strings = {};
    
        int n = input.nextInt(); // Get integer

        // Store string inputs
        int i = 0;
        while (n > 0) { 
            String s = input.nextLine();
            strings[i] = s;
            n--;
            i++;
        }

        for (int j = 0; j < strings.length; j++) { 
            System.out.println("Hello, " + strings[j] + "!");
        }

        input.close();
    }
}
