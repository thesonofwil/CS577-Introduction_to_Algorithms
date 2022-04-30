import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreeSAT {
	
    private List<Clause> clauses;
	
	public ThreeSAT(int numClauses) {
        clauses = new ArrayList<Clause>(numClauses);
    }

    public int numClausesSatisfied() {

    }
    
    private class Variable {
        int id;
        boolean negated;

        private Variable(int id) {
            this.id = id;
            
            if (id < 0) {
                this.negated = true;
            } else {
                this.negated = false;
            }
        }
    }

    private class Clause {
        List<Variable> variables;
        boolean satisfied;

        private Clause(int numVariables) {
            this.variables = new ArrayList<Variable>();
            satisfied = false;
        }

        private void addToClause(Variable v) {
            this.variables.add(v);
        }

        private boolean isSatisfied() {
            
        }
    }

	/**
     * Parse stdin and create 3SAT instance
     */
    private static ThreeSAT parse_input() {
        Scanner input = new Scanner(System.in);

        int numVariables = input.nextInt(); // total size of array
        int numClauses = input.nextInt(); // Create instance 
        ThreeSAT threeSAT = new ThreeSAT(numClauses);
        
        // Read elements and push to array
        while (numClauses > 0) {
            Variable var1 = threeSAT.new Variable(input.nextInt());
            Variable var2 = threeSAT.new Variable(input.nextInt());
            Variable var3 = threeSAT.new Variable(input.nextInt());

            Clause clause = threeSAT.new Clause(3); // 3-SAT
            clause.addToClause(var1);
            clause.addToClause(var2);
            clause.addToClause(var3);

            threeSAT.clauses.add(clause);

            numClauses--;
        }

        input.close();
        return threeSAT;
    }
	
    public static void main(String[] args) {
		ThreeSAT threeSAT = parse_input();
	}
}