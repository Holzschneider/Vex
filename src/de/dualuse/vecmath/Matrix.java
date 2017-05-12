package de.dualuse.vecmath;

abstract class Matrix<MatrixType extends Matrix<?>> 
implements Value<MatrixType,MatrixType>, MatrixAlgebra<MatrixType> 
{
	abstract public MatrixType add(MatrixType a);
	abstract public MatrixType sub(MatrixType a);
	abstract public MatrixType mul(MatrixType a);
	
	
	abstract public MatrixType set(MatrixType a);
	abstract public MatrixType fromString(String r);
	abstract public boolean elementsEqual(MatrixType o);
	public abstract MatrixType clone();
	
	@SuppressWarnings("unchecked")
	public <Q> Q to( Value<Q,MatrixType> v ) {
		return v.set((MatrixType)this);
	}
	
	public <Q> Q get( Value<Q,MatrixType> v ) { return to(v); }
	
	@Override
	public final boolean equals(Object o) {
		if (o == this)
			return true;
		else
//		if (o instanceof Matrix3d)
			return elementsEqual((MatrixType)o);
//		else
//			return false;
	}

}
