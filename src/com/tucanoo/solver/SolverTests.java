package com.tucanoo.solver;

import junit.framework.TestCase;

/**
 * Created by dave on 22/03/2017.
 */
public class SolverTests extends TestCase {
    public void testSomething() throws Exception {
        LPSolver solver = new LPSolver();

        LPProblem problem = new LPProblem(3, 3 );

        // TODO populate problem with appropriate values to perform testing

        LPProblem.Solution solution = solver.solve(problem);
        
        assert true;
    }
}
