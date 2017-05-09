package de.dualuse.vecmath;

public interface Value<T> extends Reference<T,T> {
	
	public String toString();
	public T fromString(String r);
	public T clone();
	
	public<Q> Q to( Reference<Q,T> v );
	
}
