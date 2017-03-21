package com.tucanoo.solver;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


class RowCol
  implements Serializable,
               Comparable
{
  /**
   * Constructor.
   *
   * @param r
   * @param c
   */
  RowCol ( int r,
           int c )
  {
    row = r;
    col = c;
  }

  public int compareTo ( Object irhs )
  {
    RowCol rhs = ( RowCol ) irhs;
    return ( ( row < rhs.row ) ? ( -1 )
                               : ( ( row > rhs.row ) ? 1
                                                     : ( ( col < rhs.col ) ? ( -1 )
                                                                           : ( ( col > rhs.col )
                                                                               ? 1
                                                                               : 0 ) ) ) );
  }

  int col;
  int row;
}


public class SparseMatrix
{
  /**
   * Get list of nonzero values.
   *
   * @param rows receptacle
   * @param cols receptacle
   * @param vals receptacle
   */
  public void dump ( int rows[],
              int cols[],
              double vals[] )
  {
    Set es = rep.entrySet (  );
    Iterator it = es.iterator (  );
    int i = 0;
    while ( it.hasNext (  ) )
    {
      Map.Entry me = ( Map.Entry ) it.next (  );
      RowCol rc = ( RowCol ) me.getKey (  );
      rows[i] = rc.row;
      cols[i] = rc.col;
      vals[i] = ( ( Double ) me.getValue (  ) ).doubleValue (  );
      ++i;
    }
  }


  /**
   * Get a value.
   *
   * @param row
   * @param col
   *
   * @return the value
   */
  public double get ( int row,
               int col )
  {
    Double d = ( Double ) rep.get ( new RowCol( row, col ) );
    return ( d == null ) ? 0 : d.doubleValue (  );
  }


  /**
   * Set a value.
   *
   * @param row
   * @param col
   * @param value
   */
  public void set ( int row,
             int col,
             double value )
  {
    rep.put ( new RowCol( row, col ),
              new Double( value ) );
  }


  /**
   * Number of non-zero elements.
   *
   * @return the number
   */
  public int size (  )
  {
    return rep.size (  );
  }

  private TreeMap rep = new TreeMap(  );
}
