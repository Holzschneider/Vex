package de.dualuse.vecmath;

import static org.junit.Assert.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Arrays;

import org.junit.Test;

public class Matrix3dTest {
	
	@Test public void basicTest() {
		
		Matrix3d a = new Matrix3d();
		double elements[] = {
				a.m00, a.m01, a.m02,
				a.m10, a.m11, a.m12,
				a.m20, a.m21, a.m22
		};
		
		double identity[] = {
				1.0,0.0,0.0,
				0.0,1.0,0.0,
				0.0,0.0,1.0
		};
		
		assertTrue( Arrays.equals(elements, identity) );
		
		
		a.setElements(1, 2, 3, 4, 5, 6, 7, 8, 9);
		double elements123[] = {
				a.m00, a.m01, a.m02,
				a.m10, a.m11, a.m12,
				a.m20, a.m21, a.m22
		};
		double expected123[] = {
				1,2,3,
				4,5,6,
				7,8,9
		};
		
		assertTrue( Arrays.equals(elements123, expected123) );
		
		Matrix3d b = new Matrix3d();
		b.m00 = 1; b.m01 = 2; b.m02 = 3;
		b.m10 = 4; b.m11 = 5; b.m12 = 6;
		b.m20 = 7; b.m21 = 8; b.m22 = 9;
		
		assertTrue( a.equals(b) );
		assertTrue( b.equals(a) );

		b.m11 = 1337;
		
		assertFalse( a.equals(b) );
		assertFalse( b.equals(a) );
		
		String s = a.toString();
		assertEquals("1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0",s);
		
//		b.fromString(s);
		b = Matrix3d.fromString(s);
		assertEquals(a, b);
		
		
		Matrix3d c = a.clone();
		assertTrue( c!=a );
		assertTrue( c.equals(a) );
		
		a.add(b);
		
		assertTrue( a.elementsEqual(Matrix3d.fromElements(2,4,6,8,10,12,14,16,18)) );
		
		assertFalse( a.hashCode() == b.hashCode() );
		assertTrue( a.hashCode() == a.clone().hashCode());
		
		Matrix3d m123 = Matrix3d.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9);
		assertEquals( m123, Matrix3d.fromRows(new double[][] {{1,2,3}, {4,5,6}, {7,8,9}}) );
		assertEquals( m123, Matrix3d.fromRows(new double[] {1,2,3}, new double[] {4,5,6}, new double[] {7,8,9}) );
		assertEquals( m123, Matrix3d.fromRows(new Vector3d(1,2,3), new Vector3d(4,5,6), new Vector3d(7,8,9)));
		
		Matrix3d m321 = new Matrix3d().setTransposed(m123);
		
		assertEquals( m321, Matrix3d.fromColumns(new double[][] {{1,2,3}, {4,5,6}, {7,8,9}}) );
		assertEquals( m321, Matrix3d.fromColumns(new double[] {1,2,3}, new double[] {4,5,6}, new double[] {7,8,9}) );
		assertEquals( m321, Matrix3d.fromColumns(new Vector3d(1,2,3), new Vector3d(4,5,6), new Vector3d(7,8,9)));
		
	}
	
	@Test public void matrixAlgebraTest() {
		
		Matrix3d a = Matrix3d.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Matrix3d b = Matrix3d.fromElements(11,12,13,14,15,16,17,18,19);
		Matrix3d c = new Matrix3d().setConcatenation(a,b);
		
		Matrix3d ab = Matrix3d.fromElements(90, 96, 102, 216, 231, 246, 342, 366, 390); 
		a.concatenate(b);
		
		assertTrue( c.equals(a) );
		assertTrue( a.equals(ab) );
		assertTrue( c.equals(ab) );
		
		
		Matrix3d bT = Matrix3d.fromElements(
				11,14,17,
				12,15,18,
				13,16,19);

		Matrix3d bt = new Matrix3d().setTransposed(b);
		assertEquals(bT,bt);
		
		Matrix3d dt = Matrix3d.fromRows( new double[][] {{190, 296, 3102}, {4216,5231,6246},{ 7342, 8366, 9390}} );
		assertEquals(dt.determinant(), -8464354200.0, 1e-10);
		

		Matrix3d st = Matrix3d.fromElements(1,2,3,4,5,6,7,8,9);
		Matrix3d tt = Matrix3d.fromElements(9,8,7,6,5,4,3,2,1);
		st.add(tt);
		
		assertEquals(st, Matrix3d.fromElements(10, 10, 10, 10, 10, 10, 10, 10, 10));
		
		Matrix3d mt = Matrix3d.fromElements(1,2,3,4,5,6,7,8,9);
		Matrix3d nt = Matrix3d.fromElements(9,8,7,6,5,4,3,2,1);
		
		mt.mul(nt);
		assertEquals(mt, Matrix3d.fromElements(9, 16, 21, 24, 25, 24, 21, 16, 9));
	}
	
	
	@Test public void matrixInversionTest() {
		Matrix3d m = Matrix3d.fromElements(
					1, 2, 3, 
					2, 1, 1, 
					3, 4, 5);
		
		Matrix3d i = Matrix3d.fromElements( 
				0.5, 1, -0.5,
				-3.5, -2, 2.5,
				2.5, 1, -1.5
				);

		Matrix3d j = new Matrix3d().setInverse(m);
		
		assertEquals(i,j.setInverse(m));
		assertEquals(i,m.invert());
		
		assertEquals(i.invert(),m.invert());
	}

	@Test public void matrixVectorTest() {	
		AffineTransform at = new AffineTransform();
		at.translate(10, 10);
		at.rotate(10,4,4);
		at.scale(3,3);
		at.rotate(1);
		
		
		Matrix3d m = new Matrix3d();
		m.translate(10, 10);
		m.rotate(10,4,4);
		m.scale(3,3);
		m.rotate(1);
		
		Point2D p = at.transform(new Point2D.Double(1,1), new Point2D.Double());
		Vector2d q = m.project(new Vector2d(1,1));
		assertTrue( q.x == p.getX() && q.y == p.getY() );

		double[] pts = {1,2, 3,4, 5,6, 7,8};
		double[] qts = m.project(pts.clone());
		
		at.transform(pts, 0, pts, 0, pts.length/2);
		
		assertArrayEquals(pts, qts, 1e-10);
		
		Vector2d w = new Vector2d(10,10);
		Vector2d v1 = m.intersect(w.clone());
		Vector2d v2 = m.invert().project(w.clone());
		assertArrayEquals(v1.get(new double[2]),v2.get(new double[2]),1e-10);
		
	}
	
}


