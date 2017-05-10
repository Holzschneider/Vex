package de.dualuse.vecmath;

public interface MatrixAlgebra<Q> {
	public Q zero();
	public Q identity();

	public Q invert();
	public Q inversion(Q that);
	
	public Q transpose();
	public Q transposition(Q that);
	
	public Q concatenate(Q that);
	public Q concatenation(Q A, Q B);
	
	public double determinant();
}
