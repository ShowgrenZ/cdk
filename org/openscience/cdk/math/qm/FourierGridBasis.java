/* FourierGridBasis.java
 * 
 * Autor: Stephan Michels 
 * EMail: stephan@vern.chem.tu-berlin.de
 * Datum: 2.7.2001
 * 
 * Copyright (C) 1997-2001  The Chemistry Development Kit (CDK) project
 * 
 * Contact: steinbeck@ice.mpg.de, gezelter@maul.chem.nd.edu, egonw@sci.kun.nl
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *  */

package org.openscience.cdk.math.qm;

import org.openscience.cdk.math.*;
 
/**
 * At his time the class don't work correcly
 *
 * The theory were from 
 * C.C.Marston; J.Chem.Phys. Vol.91; (1989) 3571
 * 
 */
public class FourierGridBasis implements Basis
{
	private int N;
	private double minx;
	private double maxx;
	private double dx;
	private Function potential;
	private Vector basis;

	private Matrix S;
	private Matrix T;
	private Matrix V;

	//private final static double m = 1.6726485E-27; // [kg] Masse eines Protons
	//private final static double m = 9.109534E-31; // [kg] Masse eines Elektrons
	private final static double m = (1.6726485E-27*1.6726485E-27)/(1.6726485E-27+1.6726485E-27); // [kg]red.Masse
	private final static double hdash = 6.582E-16; // [eV*s]

	private double Tl;
  private int N2;

	public FourierGridBasis(int N, double minx, double maxx, Function potential)
	{
		this.N = N;
		this.minx = minx;
		this.maxx = maxx;
		dx = (maxx-minx)/N;
		this.potential = potential;

		//Tl = (2/m)*(hdash*Math.PI/(N*dx))*(hdash*Math.PI/(N*dx));
		Tl = 2*(Math.PI/(N*dx))*(Math.PI/(N*dx))/10;
    N2 = (N-1)/2;

		/*S = calcS();
		T = calcT();
		V = calcV();*/
	}

	public int getSize()
	{
		return N;
	}

	public double getMinX()
  { 
    return minx;
  }
  
  public double getMaxX()
  { 
    return maxx;
  }
  
  public double getMinY()
  { 
    return -1d;
  }
  
  public double getMaxY()
  { 
    return +1d;
  }
  
  public double getMinZ()
  { 
    return -1d;
  }
  
  public double getMaxZ()
  { 
    return +1d;
  }

	public double getValue(int index, double x, double y, double z)
	{
		if ((index*dx+minx<=x) && (x<=index*dx+maxx+dx))
			return 1d;
		return 0d;
	}

	public Vector getValues(int index, Matrix m)
  {
		if (m.columns<1)
			return null;

		Vector result = new Vector(m.columns);
		for(int i=0; i<m.columns; i++)
    	if ((index*dx+minx<=m.matrix[0][i]) && (m.matrix[0][i]<=index*dx+maxx+dx))
      	result.vector[i] = 1d;
    	else result.vector[i] = 0d;

		return result;
  }

	public double calcS(int i, int j)
	{
		if (i==j)
      return 1d;
    return 0d;
	}

	/*public Matrix calcS()
	{
		int i,j;
		Matrix S = new Matrix(N,N);
    for(i=0; i<N; i++)
      for(j=0; j<N; j++)
        if (i==j)
          S.matrix[i][j] = 1d;
        else
          S.matrix[i][j] = 0d;

		return S;
	}*/

	public double calcJ(int i, int j)
	{
    double result = 0d;
    for(int l=1; l<=N2; l++)
      result += Math.cos(l*2*Math.PI*(i-j)/N)*Tl*l*l;
    return result * 2d/N;
  }

	/*public Matrix calcT()
	{
		int i,j,l; 
    Matrix T = new Matrix(N,N);
		double Tl = (2/m)*(hdash*Math.PI/(N*dx))*(hdash*Math.PI/(N*dx));
		int n = (N-1)/2;

    for(i=0; i<N; i++)
      for(j=0; j<N; j++) 
			{
				T.matrix[i][j] = 0d;
				for(l=1; l<=n; l++)
	        T.matrix[i][j] += Math.cos(l*2*Math.PI*(i-j)/N)*Tl*l*l;
				T.matrix[i][j] *= 2d/N;
			}
    return T;
	}*/

	public double calcV(int i, int j)
	{ 
		if (i==j)
			return potential.getValue(minx+dx*i, 0d, 0d);
		return 0d;
  }

	/*public Matrix calcV()
	{
		int i,j;
		Matrix V = new Matrix(N,N);
		for(i=0; i<N; i++)
			for(j=0; j<N; j++)
				V.matrix[i][j] = 0d;
		for(i=0; i<N; i++)
			V.matrix[i][i] = potential.getValue(minx+dx*i, 0d, 0d);
		return V;
	}*/

	/*public Matrix getS()
	{
		return S;
	}

	public Matrix getT()
	{
		return T;
	}

	public Matrix getV()
	{
		return V;
	}*/

	public double calcI(int i, int j, int k, int l)
	{
		return 0d;
	}
}
