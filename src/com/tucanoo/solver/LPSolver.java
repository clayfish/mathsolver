package com.tucanoo.solver;


import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by dave on 21/03/2017.
 */
public class LPSolver {

    private int acols[];
    private int arows[];
    private double avals[];
    private double b[]; // constraint threshold
    private double c[]; // objective fn
    private String collab[]; // column labels
    private double l[]; // lower bounds
    private String rowlab[]; // row labels
    private int rowmark[]; // type of constraint (0=objective 1=greater 2=less)
    private double u[]; // upper bounds
    private double x[]; // solution
    private int asize;
    private int infeasible;
    private int m; // constraints = # rows
    private int maxflag; // whether to maximize
    private int n; // variables = # columns

    public LPProblem.Solution solve(LPProblem problem) {

        m = problem.getM();
        n = problem.getN();
        maxflag = problem.getMaxflag();
        rowmark = problem.getRowmark();
        b = problem.getB();
        c = problem.getC();
        l = problem.getL();
        u = problem.getU();
        rowlab = problem.getRowlab();
        collab = problem.getCollab();

        arows = new int[problem.getA().size()];
        acols = new int[problem.getA().size()];
        avals = new double[problem.getA().size()];
        asize = problem.getA().size();

        problem.getA().dump(arows, acols, avals);

        x = new double[n];
        for (int i = 0; i < n; ++i) {
            x[i] = 0;
        }

        if ((n > 0) && (m > 0)) { // just in case

            // in here we will prepare the parameters to pass to Apache Math and call solve()

            // code below is commented as this is what needs to be developed - this was just an initial attempt.

//            // establish objective
//            LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(problem.c, 0);
//
//            // define constraints
//            Collection constraints = new ArrayList();
//
//            // cast int array to double array(testing)
//            double[] colsArray = new double[problem.getA().size()];
//            for (int i = 0; i < problem.getA().size(); ++i) {
//                colsArray[i] = acols[i];
//            }
//
//            // loop over data adding our constraints
//            for (int i = 0; i < problem.m; i++) {
//
//                // determine type of constraint
//                Relationship relationship = null;
//                if (problem.rowmark[i] == 0)
//                    relationship = Relationship.EQ;
//                else if (problem.rowmark[i] == 1)
//                    relationship = Relationship.GEQ;
//                else if (problem.rowmark[i] == 2)
//                    relationship = Relationship.LEQ;
//
//                constraints.add(new LinearConstraint(colsArray,
//                                    relationship,
//                                    avals[i]));
//            }
//
//            LinearConstraintSet linearConstraintSet = new LinearConstraintSet(constraints);
//            // initiate solver and obtain our solution
//            SimplexSolver ss = new SimplexSolver();
//
//            int infeasible = 0; // feasible
//
//
//            PointValuePair result = null;
//
//            try {
//                result = ss.optimize(objectiveFunction, linearConstraintSet, GoalType.MINIMIZE);
//                result = ss.doOptimize();
//            } catch (TooManyIterationsException e) {
//                infeasible = 1;
//                e.printStackTrace();
//            } catch (UnboundedSolutionException e) {
//                infeasible = 1;
//                e.printStackTrace();
//            } catch (NoFeasibleSolutionException e) {
//                infeasible = 1;
//                e.printStackTrace();
//            }
//
//
//            System.out.println(result.toString());
//
//
//            // need to process apache results into x[] and set infeasible
//            return problem.new Solution(result.getPoint(), infeasible);


        } else {
            System.err.println("MIPProblem.solve: n=" + n + ", m=" + m);
            return null;
        }

    }
}
