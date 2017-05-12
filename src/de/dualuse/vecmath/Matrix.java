package de.dualuse.vecmath;

abstract class Matrix<MatrixType extends Matrix<MatrixType>>
extends Tuple<MatrixType>
implements MatrixAlgebra<MatrixType> 
{
	abstract public MatrixType add(MatrixType a);
	abstract public MatrixType sub(MatrixType a);
	abstract public MatrixType mul(MatrixType a);
	
	
}
