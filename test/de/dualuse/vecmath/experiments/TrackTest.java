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
			
			Vector2d[] ABCD = {A,B,C,D};
			
			Track<Vector2d> track = new Track<Vector2d>();
			
			
			MouseAdapter ml = new MouseAdapter() {
				MouseEvent down = null;
				Vector2d captured = null;
				@Override
				public void mousePressed(MouseEvent e) {
					captured = null;
					for (Vector2d v: ABCD)
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
				for (Vector2d p: ABCD)
					g2.draw(new Ellipse2D.Double(p.x-R, p.y-R, 2*R, 2*R));
				
				
				track.put(0, null, A, B);
				track.put(1, C, D, null);
//				track.put(2, null, C, null);
//				track.put(3, null, D, null);

				Vector2d v = new Vector2d();
//				track.get(0.5, v);
				
				Path2D.Double p = new Path2D.Double();
				p.moveTo(A.x, A.y);
				
				for (double i=0,s=0.05;i<1+s;i+=s) {
					track.get(i, v);
					p.lineTo(v.x, v.y);
					
					g2.draw(new Ellipse2D.Double(v.x-r, v.y-r, 2*r, 2*r));
					System.out.println(i);
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
