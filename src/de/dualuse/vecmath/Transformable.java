package de.dualuse.vecmath;

public interface Transformable<T,R> {
	public R transformation(T matrixlike);
}
