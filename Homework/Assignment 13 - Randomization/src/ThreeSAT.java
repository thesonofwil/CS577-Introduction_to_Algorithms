import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ThreeSAT {
	
    private static List<Clause> clauses;
    private static List<Integer> varAssignments;
	
	public ThreeSAT(int numClauses, int numVariables) {
        clauses = new ArrayList<Clause>(numClauses);
        varAssignments = new ArrayList<Integer>(numVariables);
    }
    
    private class Variable {
        int id;
        boolean negated;

        private Variable(int id) {
            this.id = Math.abs(id);
            
            if (id < 0) {
                this.negated = true;
            } else {
                this.negated = false;
            }
        }

        /**
         * 
         * @param assignment how the variable should be assigned, 1 or -1
         * @return true/false if variable is/isn't satisfied
         */
        private boolean getBoolean(int assignment) {
            if (assignment == 1 && !this.negated) {
                return true;
            } else if (assignment == 1 && this.negated) {
                return false;
            } else if (assignment == -1 && !this.negated) {
                return false;
            } else { // assignment == -1 && this.negated
                return true;
            }
        }
    }

    private class Clause {
        List<Variable> variables;
        List<Boolean> state; // track if after assignment each variable is T/F

        private Clause(int numVariables) {
            this.variables = new ArrayList<Variable>(numVariables);
            this.state = new ArrayList<Boolean>(numVariables);
        }

        private void addToClause(Variable v) {
            this.variables.add(v);
        }

        private void setVariables() {
            for (Variable var : this.variables) {
                int index = var.id - 1;
                System.out.println("Index: " + index);
                int assignment = varAssignments.get(index);
                boolean val = var.getBoolean(assignment);
                
                this.state.add(val);
            }
        }

        /**
         * Checks if the clause is satisfied. A clause in 3SAT is formed as
         * x1 || x2 || x3
         * @return
         */
        private boolean isSatisfied() {
            return (state.get(0) || state.get(1) || state.get(2));
        }
    }

	/**
     * Parse stdin and create 3SAT instance
     */
    private static void parse_input() {
        Scanner input = new Scanner(System.in);

        int numVariables = input.nextInt(); // total size of array
        int numClauses = input.nextInt(); // Create instance 
        ThreeSAT threeSAT = new ThreeSAT(numClauses, numVariables);
        
        // Read elements and push to array
        while (numClauses > 0) {
            Variable var1 = threeSAT.new Variable(input.nextInt());
            Variable var2 = threeSAT.new Variable(input.nextInt());
            Variable var3 = threeSAT.new Variable(input.nextInt());

            Clause clause = threeSAT.new Clause(3); // 3-SAT
            clause.addToClause(var1);
            clause.addToClause(var2);
            clause.addToClause(var3);

            clauses.add(clause);

            numClauses--;
        }

        input.close();
    }
	
    private static int numClausesSatisfied() {
        int count = 0;

        for (Clause c : clauses) {
            if (c.isSatisfied()) {
                count++;
            }
        }

        return count;
    }

    /**
     * Randomly assigns each variable a value of 1 or -1
     */
    private static void assignLiterals() {
        Random r = new Random();
        int values[] = {-1, 1};

        for (int i = 0; i < varAssignments.size(); i++) {
            int num = r.nextInt(1); // randomly generate index of 0 or 1
            int assignment = values[num];
            varAssignments.add(i, assignment);
        }

        System.out.println("Assignments: " + varAssignments.toString());
    }

    /**
     * After assigning values to literals, sets the variables in each clauses those values
     */
    private static void setClauses() {
        for (Clause c : clauses) {
            c.setVariables();
        }
    }

    public static void main(String[] args) {
		parse_input();

        // Keep looping until we find 7/8 clauses have been satisfied
        while (true) {
            assignLiterals();
            setClauses();
            int numSatisfied = numClausesSatisfied();
            if (numSatisfied >= (7 / 8) * clauses.size()) {
                break;
            }
        }

        // If we reach this point, we have a good enough assignment
        for (int i = 0; i < varAssignments.size(); i++) {
            if (i == varAssignments.size()) {
                System.out.println(varAssignments.get(i));
            } else {
                System.out.print(varAssignments.get(i) + " ");
            }
        }
	}
}