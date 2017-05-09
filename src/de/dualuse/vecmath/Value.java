package de.dualuse.vecmath;

public interface Value<T> {
	
	public String toString();
	public T fromString(String r);
	public T clone();
	
	public static interface Function<Q,T> { Q set(T v); }
	public<Q> Q get( Function<Q,T> v );

}
