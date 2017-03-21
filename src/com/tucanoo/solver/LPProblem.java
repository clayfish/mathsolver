package com.tucanoo.solver;



// LP Problem represent the problem to solve
public class LPProblem
{
  /**
   * Constructor.
   *
   * @param nconstraints number of constraints
   * @param nvariables number of variables
   */
  public LPProblem(int nconstraints,
                   int nvariables )
  {
    m = nconstraints;
    n = nvariables;

    rowmark = new int[m];
    rowmark[0] = 2;
    for ( int i = 1; i < m; ++i )
    {
      rowmark[i] = 1;
    }

    b = new double[m];
    for ( int i = 0; i < m; ++i )
    {
      b[i] = 0;
    }

    A = new SparseMatrix(  );

    c = new double[n];
    for ( int i = 0; i < n; ++i )
    {
      c[i] = 1;
    }

    l = new double[n];
    for ( int i = 0; i < n; ++i )
    {
      l[i] = 0;
    }

    u = new double[n];
    for ( int i = 0; i < n; ++i )
    {
      u[i] = 1;
    }

    rowlab = new String[m];
    for ( int i = 0; i < m; ++i )
    {
      rowlab[i] = new String( "Row " ) + Integer.toString ( i );
    }

    collab = new String[n];
    for ( int i = 0; i < n; ++i )
    {
      collab[i] = new String( "Col " ) + Integer.toString ( i );
    }
  }

  // instance variables
    
  SparseMatrix A;
  double b[]; // constraint threshold
  double c[]; // objective fn
  String collab[]; // column labels
  double l[]; // lower bounds
  String rowlab[]; // row labels
  int rowmark[]; // type of constraint (0=objective 1=greater 2=less)
  double u[]; // upper bounds
  int m; // constraints = # rows
  int maxflag = -1; // whether to maximize
  int n; // variables = # columns
  private volatile StringBuffer sb;



  /**
   * Pretty output of the lp problem.
   *
   * @return matrices in html
   */
  public String html (  )
  {
    sb = new StringBuffer(  );
    sb.append ( "<table rules=\"all\" frame=\"box\">" );
    begrow (  );
    td ( "label" );
    for ( int c = 0; c < n; ++c )
    {
      td ( collab[c] );
    }

    td ( "target" );
    endrow (  );
    for ( int r = 0; r < m; ++r )
    {
      begrow (  );
      td ( rowlab[r] );
      for ( int c = 0; c < n; ++c )
      {
        td ( A.get ( r, c ) );
      }

      td ( marks[rowmark[r]] + b[r] );
      endrow (  );
    }

    begrow (  );
    td ( "up" );
    for ( int c = 0; c < n; ++c )
    {
      td ( u[c] );
    }

    td ( "" );
    endrow (  );
    begrow (  );
    td ( "low" );
    for ( int c = 0; c < n; ++c )
    {
      td ( l[c] );
    }

    td ( "" );
    endrow (  );
    sb.append ( "</table>" );
    return sb.toString (  );
  }


  public String toString (  )
  {
    return html (  );
  }


  private void begrow (  )
  {
    sb.append ( "<tr>" );
  }


  private void endrow (  )
  {
    sb.append ( "</tr>" );
  }


  private static String stringOf ( double a[] )
  {
    StringBuffer sb = new StringBuffer(  );
    sb.append ( "{" );
    for ( int i = 0; i < a.length; ++i )
    {
      if ( i > 0 )
      {
        sb.append ( "," );
      }

      sb.append ( a[i] );
    }

    sb.append ( "}" );
    return sb.toString (  );
  }


  private void td ( String s )
  {
    sb.append ( "<td> " + escape ( s ) + " </td>" );
  }

  // copied from vicar to avoid unexplained classpath cycle problem
  private static String escape(String text) {
    StringBuffer sb = new StringBuffer();
    if (text == null) {
      text = "<null>";
    }
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i); if (ch == '<') {
        sb.append("&lt;"); } else if (ch == '>') {
          sb.append("&gt;"); } else if (ch == '&') {
            sb.append("&amp;"); } else {
              sb.append(ch); }
    }
    return sb.toString();
  }

  private void td ( double d )
  {
    td ( Double.toString ( d ) );
  }




  public class Solution
  {
    public Solution ( double x[],
               int infeasible )
    {
      this.x = x;
      this.infeasible = infeasible;
    }

    public void check ( double tol ) {
      for ( int c = 0; c < n; ++c ) {
        double s = x[c];
        if ( s < l[c] - tol )
          System.err.println ( "check: " + collab[c] + ": " + s + " < " + l[c] );
        if ( s > u[c] + tol )
          System.err.println ( "check: " + collab[c] + ": " + s + " > " + u[c] );
      }

      double[] forms = new double[m];
      int[] arows = new int[A.size()];
      int[] acols = new int[A.size()];
      double[] avals = new double[A.size()];
      A.dump(arows,acols,avals);
      for ( int i = 0; i < A.size(); ++i ) {
        forms[arows[i]] += x[acols[i]] * avals[i];
      }
      for ( int r = 0; r < m; ++r ) {
        if ( rowmark[r] == 1 ) {
          if ( forms[r] > b[r] + tol )
            System.err.println ( "check: " + rowlab[r] + ": " + forms[r]
                + " > " + b[r] );
        } else if ( rowmark[r] == 2 ) {
          if ( forms[r] < b[r] - tol )
            System.err.println ( "check: " + rowlab[r] + ": " + forms[r]
                + " < " + b[r] );
        } else {
        }
      }
    }
    public String html (  )
    {
      sb = new StringBuffer(  );
      sb.append ( "<table rules=\"all\" frame=\"box\"><tbody><tr>" );
      for ( int i = 0; i < x.length; ++i )
      {
        sb.append ( "<td>" + x[i] + "</td>" );
      }

      return sb.toString (  ) + "</tr></tbody></table>";
    }



    public String toString (  )
    {
      return (infeasible==1 ? "infeasib" : "feasible") + ": " + stringOf ( x );
    }

    public double x[]; // solution
    public int infeasible = 0;
  }

  static final String marks[] = { "O", ">", "<" };
  static double maxbnd = 1.1e30;
  /**
   * @return
   */
  public static String[] getMarks()
  {
    return marks;
  }

  /**
   * @return
   */
  public static double getMaxbnd()
  {
    return maxbnd;
  }

  /**
   * @return
   */
  public SparseMatrix getA()
  {
    return A;
  }

  /**
   * @return
   */
  public double[] getB()
  {
    return b;
  }

  /**
   * @return
   */
  public double[] getC()
  {
    return c;
  }

  /**
   * @return
   */
  public String[] getCollab()
  {
    return collab;
  }

  /**
   * @return
   */
  public double[] getL()
  {
    return l;
  }

  /**
   * @return
   */
  public int getM()
  {
    return m;
  }

  /**
   * @return
   */
  public int getMaxflag()
  {
    return maxflag;
  }

  /**
   * @return
   */
  public int getN()
  {
    return n;
  }

  /**
   * @return
   */
  public String[] getRowlab()
  {
    return rowlab;
  }

  /**
   * @return
   */
  public int[] getRowmark()
  {
    return rowmark;
  }

  /**
   * @return
   */
  public double[] getU()
  {
    return u;
  }

  /**
   * @param d
   */
  public static void setMaxbnd(double d)
  {
    maxbnd = d;
  }

  /**
   * @param matrix
   */
  public void setA(SparseMatrix matrix)
  {
    A = matrix;
  }

  /**
   * @param ds
   */
  public void setB(double[] ds)
  {
    b = ds;
  }

  /**
   * @param ds
   */
  public void setC(double[] ds)
  {
    c = ds;
  }

  /**
   * @param strings
   */
  public void setCollab(String[] strings)
  {
    collab = strings;
  }

  /**
   * @param ds
   */
  public void setL(double[] ds)
  {
    l = ds;
  }

  /**
   * @param i
   */
  public void setM(int i)
  {
    m = i;
  }

  /**
   * @param i
   */
  public void setMaxflag(int i)
  {
    maxflag = i;
  }

  /**
   * @param i
   */
  public void setN(int i)
  {
    n = i;
  }

  /**
   * @param strings
   */
  public void setRowlab(String[] strings)
  {
    rowlab = strings;
  }

  /**
   * @param is
   */
  public void setRowmark(int[] is)
  {
    rowmark = is;
  }

  /**
   * @param ds
   */
  public void setU(double[] ds)
  {
    u = ds;
  }

}
