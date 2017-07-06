package de.dualuse.vecmath;

public interface TransformAlgebra<Q,T,V> {
	public Q setIdentity();

	public Q invert();
	public Q setInverse(Q that);
	
	public Q concatenate(Q that);
	public Q setConcatenation(Q A, Q B);
	
	
	public T transform( T v );
	public T transform( T v, T w );

	public Q applyTransformation( T v );
	public Q applyTransformation( T v, T w );
	
}
