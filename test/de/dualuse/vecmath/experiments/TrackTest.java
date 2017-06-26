package de.dualuse.vecmath.experiments;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import de.dualuse.vecmath.Track;
import de.dualuse.vecmath.Track.Boundary;
import de.dualuse.vecmath.Vector2d;

public class TrackTest {
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		
		
		f.setContentPane(new JComponent() {
			private static final long serialVersionUID = 1L;

			int R = 5, r = 2;
			Vector2d A = new Vector2d(100,100);
			Vector2d B = new Vector2d(200,100);
			Vector2d C = new Vector2d(300,200);
			Vector2d D = new Vector2d(500,500);
			Vector2d E = new Vector2d(500,300);
			Vector2d F = new Vector2d(100,200);
			
			Vector2d[] ABCDEF = {A,B,C,D, E,F};
			
			
			MouseAdapter ml = new MouseAdapter() {
				MouseEvent down = null;
				Vector2d captured = null;
				@Override
				public void mousePressed(MouseEvent e) {
					captured = null;
					for (Vector2d v: ABCDEF)
						if (v.distance(e.getX(),e.getY())<R*1.5)
							captured = v;
					
					down = e;
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					if (captured!=null) 
						captured.add( e.getX()-down.getX(), e.getY()-down.getY());
					
					down = e;
					repaint();
				}
			};
			
			{
				addMouseListener(ml);
				addMouseMotionListener(ml);
			}
			
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
				
				g2.setStroke(new BasicStroke(4));
				for (Vector2d p: ABCDEF)
					g2.draw(new Ellipse2D.Double(p.x-R, p.y-R, 2*R, 2*R));
				
				Track<Vector2d> track = new Track<Vector2d>();

				double path = 0;
				track.put(0, null, A,  null); //A.clone().adds(v, s));
				
				Vector2d J = A;
				
				for (int i=1,I=ABCDEF.length-1;i<I;i++) {
					Vector2d P = ABCDEF[i];
					
					Vector2d O = P.clone().adds(ABCDEF[i+1],-1/6d).adds(ABCDEF[i-1],+1/6d);
					Vector2d Q = P.clone().adds(ABCDEF[i+1],+1/6d).adds(ABCDEF[i-1],-1/6d);
					
//					path+= J.distance(O)+O.distance(P)+P.distance(Q);
					path+=1;
					
					J = Q;
					track.put(path, O,P,Q);
				}
				
				path+= 1;
//				path+= J.distance( F );
				track.put(path, null, F,  null); //A.clone().adds(v, s));
				
//				Vector2d w = B.clone().adds(A.clone().sub(B),1/6d).adds(B.clone().sub(C), 1/6d);
//				g2.fill(new Ellipse2D.Double(w.x-r, w.y-r, 2*r, 2*r));
				
//				B+(A-B)/6+(B-C)/6 = B+A/6-C/6
				
//				double S = 1/6d;
//				track.put(0, 
//						null, 
//						B, 
//						B.clone().adds(A,-S).adds(C, S)
//					);
//				
//				track.put(
//						1, 
//						C.clone().adds(D, -S).adds(B, S),
//						C, 
//						C.clone().adds(B,-S).adds(D, S)
//					);
//
//				track.put(
//						2, 
//						D.clone().adds(E, -S).adds(C, S),
//						D, 
//						D.clone().adds(E, S).adds(C, -S)
//					);
//				
//				track.put(
//						3, 
//						E.clone().adds(F, -S).adds(D, S),
//						E, 
//						null);

				
				
				Vector2d v = new Vector2d();
				Path2D.Double p = new Path2D.Double();
				p.moveTo(A.x, A.y);

				track.setLowerBound(Boundary.CLAMP);
				track.setUpperBound(Boundary.CLAMP);
				
				for (double i=0,s=path/100d;i<path+s;i+=s) {
					track.get(i, v);
					
					if (i==0) p.moveTo(v.x, v.y);
					else p.lineTo(v.x, v.y);
					
					g2.fill(new Ellipse2D.Double(v.x-r, v.y-r, 2*r, 2*r));
				}
				
				g2.setStroke(new BasicStroke(0.66f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1, new float[] { 5 },0));
				g2.draw(p);
				
				g2.dispose();
			}
			
		});
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(1200, 150, 800, 800);
		f.setVisible(true);
		
	}
}
