package de.dualuse.vecmath;

import java.awt.geom.Point2D;

import org.junit.Assert;
import org.junit.Test;

public class Matrix3dTest {
	
	@Test public void matrixInversionTest() {
		
		Matrix3d m = new Matrix3d(
					1, 2, 3, 
					2, 1, 1, 
					3, 4, 5);
		
		Matrix3d i = new Matrix3d( 
				0.5, 1, -0.5,
				-3.5, -2, 2.5,
				2.5, 1, -1.5
				);

		
		
//		new Vector2d(1,2).projection( i ).to ( (v) -> ... );
		
		
		
		
		
//		i.project( new Vector2d(1,2) ).to( (v) -> new Point2D.Double(v,x,v.y)  );
		
//		Point2D p = w.to( new Reference<Point2D, Vector2d>() {
//			@Override
//			public Point2D from(Vector2d v) {
//				return new Point2D.Double(v.x,v.y);
//			}
//		});
		
		
//		w.to( (v) -> new Point2D.Double(v,x,v.y) );
		
		
		
		
		
		
		
//		Assert.assertEquals(i.toString(), n.toString());
		Assert.assertEquals(i,m.invert());

////		new Vector2d(1,2).transformBy(i).to( (v) -> new Point2.Double(v.x,v.y) );
//		i.project(new Vector2d(1,2)).to( new Reference<Point2D, Vector2d>() {
//			public Point2D from(Vector2d v) {
//				return new Point2D.Double(v.x,v.y);
//			}
//		});
		
	}
	
	public static void main(String[] args) {
		new Matrix3dTest().matrixInversionTest();
	}
}


