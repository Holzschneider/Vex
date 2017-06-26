package de.dualuse.vecmath;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Track<A> extends TreeMap<Double, Track.Sample<A>> implements Animation<Interpolatable<? super A>>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	static public class Sample<Q> implements Serializable {
		private static final long serialVersionUID = 1L;
			
		final Q p, v, q;

		public Sample(Q x) {
			this.v = x;
			q = p = null;
		}
		
		public Sample(Q x, Q dx) {
			this.v = x;
			this.p = dx;
			this.q = dx;
		}
		
		public Sample(Q x, Q px, Q qx) {
			this.v = x;
			this.p = px;
			this.q = qx;
		}
		
		public String toString() {
			return "("+p+")->("+v+")<-("+q+")";
		}
	}
	
	
	public<R extends Interpolatable<? super A>> R get(double t, R v) {
		if (size()==1) {
			v.set(this.firstEntry().getValue().v);
			return v;
		}
		
		t = timeTransform(t);

		Map.Entry<Double, Sample<A>> leftEntry = this.floorEntry(t);
		Map.Entry<Double, Sample<A>> rightEntry = this.higherEntry(t);
		
		if (leftEntry == null) { //outofbounds: t < start
			leftEntry = rightEntry;
			rightEntry = this.higherEntry(leftEntry.getKey());
		}

		if (rightEntry == null) { //outofbounds: t > start
			rightEntry = leftEntry;
			leftEntry = this.lowerEntry(rightEntry.getKey());
		}
				
		Sample<A> leftSample = leftEntry.getValue(), rightSample = rightEntry.getValue();
		double lt = leftEntry.getKey(), rt = rightEntry.getKey();
		
		v.spline(leftSample.v, leftSample.q, rightSample.p, rightSample.v, (t-lt) / (rt - lt) );
		
		return v;
	}
	
	
//	public Track.Sample<A> put(int key, Sample<A> value) { return super.put((double)key, value); }
//	public Track.Sample<A> put(float key, Sample<A> value) { return super.put((double)key, value); }
//	public Track.Sample<A> put(long key, Sample<A> value) { return super.put((double)key, value); }
//	
//	public Track<A> put(double key, A value) { super.put(key, new Track.Sample<A>(value,null)); return this; }
//	public Track<A> put(double key, A value, A handleVector) { super.put(key, new Track.Sample<A>(value,handleVector)); return this; }
	
	//TODO also support Corner-Samples by not specifying controlpoints, or allowing control points to be null 
	public Track<A> put(double key, A leftControl, A vertex, A rightControl) { super.put(key, new Track.Sample<A>(vertex,leftControl, rightControl)); return this; }
	
	
	public double start() { return this.firstKey(); }
	public double end() { return this.lastKey(); }
	public double length() { return end()-start(); }

	
	static public enum Boundary {
		CONTINUE,
		CLAMP,
		LOOP,
		PINGPONG
	}
	
	private Boundary startBehavior = Boundary.CONTINUE;
	private Boundary endBehavior = Boundary.CONTINUE;
	
	public void setLowerBound( Boundary behavior ) { startBehavior = behavior; }
	public Boundary getLowerBound( Boundary behavior ) { return startBehavior; }
	
	public void setUpperBound( Boundary behavior ) { endBehavior = behavior; }
	public Boundary getUpperBound() { return endBehavior; }
	
	public void setBounds( Boundary lower, Boundary upper) {
		startBehavior = lower;
		endBehavior = upper;
	}
	
	
	private TransitionTimingFunction function = TransitionTimingFunction.Linear;
	public void setTransition( TransitionTimingFunction timingFunction ) {
		function = timingFunction;
	}
	
	private double timeTransform(double t) {
		double start = 0, end = 0, length = 0, u = 0;
		
		if (startBehavior != Boundary.CONTINUE || endBehavior != Boundary.CONTINUE) {
			start = start();
			end = end();
			length = end-start;
		}
		
		u = t;
		
		if (t<start)
			switch (startBehavior) {
			default:
			case CONTINUE: u = t; break;
			case CLAMP: u = start; break; 
			case LOOP: u = (((t-start)%length)+length)+start; break;
			case PINGPONG: u = (((t-start)%length)+length); u = (((int)(-(t-start)/length))%2==1?u:(end-start)-u)+start; break;
			}
		
		if (t>end) 
			switch (endBehavior) {
			default:
			case CONTINUE: u = t; break;
			case CLAMP: u = end; break; 
			case LOOP: u = (((t-start)%length))+start; break;
			case PINGPONG: u = (((t-start)%length)); u = (((int)((t-start)/length))%2==0?u:(end-start)-u)+start; break;
			}
		
		return function.f(u);
	}
	
	
	
	
	
/*
	public static void main(String[] args) throws Exception {
		
		final Track<Float> tn = new Track<Float>();
		tn.put(0., new Sample<Float>(0f,0.2f)  );
		tn.put(100, new Sample<Float>(1f,0.0f)  );
		tn.put(200., new Sample<Float>(0.5f,0.0f)  );
		tn.put(300., new Sample<Float>(0f,-0.1f)  );
		

//		Transparency t = new Transparency();
//		t.transparency.animate(tn);
		
//		tv.get(11.3, v);
		
		
		final Track<Value2> wv = new Track<Value2>();

		wv.put(0., new Sample<Value2>(new Vec2.Double(100,100), new Vec2.Double(0,-50))  );
		wv.put(100, new Sample<Value2>(new Vec2.Double(200,100), new Vec2.Double(80,-80), new Vec2.Double(80,00))  );
		wv.put(200., new Sample<Value2>(new Vec2.Double(150,150), new Vec2.Double(0,30) )  );
	 	wv.put(300., new Sample<Value2>(new Vec2.Double(300,200), new Vec2.Double(0,30) )  );

		wv.put(400, new Vec2.Double(300,250), new Vec2.Double(-200/3,0), null);
		wv.put(500, new Vec2.Double(100,300) );
		wv.put(600, new Vec2.Double(50,330), new Vec2.Double(-100/3,0));
		
//		test(wv);
		
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("bla.track")));
//		oos.writeObject(wv);
//		oos.close();
		
//		ObjectOutputStream xoos = new XStream().createObjectOutputStream(new FileOutputStream(new File("bla.trax")));
//		xoos.writeObject(wv);
//		xoos.close();
		
//		final Track<Value2> wv = ((Track<Value2>)new ObjectInputStream(new FileInputStream(new File("bla.track"))).readObject());
//		final Track<Value2> wv = ((Track<Value2>)new XStream().createObjectInputStream(new FileInputStream(new File("bla.trax"))).readObject());
		
		
//		System.out.println(wv.timeTransform(-));
		
//		testAnimation(wv);
//		testTrack(wv);
		
//		wv.get(100, new Vec2.Double());
		
		JFrame f = new JFrame();
	
		f.getContentPane().add(new JComponent() {
			

			Ellipse2D e = new Ellipse2D.Double();
			Vec2.Double v2 = new Vec2.Double();
//			Scalar.Double sd = new Scalar.Double();
			protected void paintComponent(Graphics g) {
				
				Graphics2D g2 = (Graphics2D) g;
				g2.scale(2, 2);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
				
				
				for (double i=wv.start();i<wv.end();i++) {
//					float f = Geometry.clamp(tn.get(i, sd).floatValue(),0,1);
//					g2.setColor(new Color(f,f,f));
					g2.setColor(Color.BLACK);
					
					wv.get(i, v2);
					e.setFrameFromCenter(v2.x, v2.y, v2.x+1.,v2.y+1);
					g2.fill(e);
					
				}
				
				g2.setColor(Color.BLUE);
				for (Map.Entry<Double,Sample<Value2>> nodes: wv.entrySet()) {
					Value2 v = nodes.getValue().v;
					
					e.setFrameFromCenter(v.getX(),v.getY(),v.getX()+5,v.getY()+5);
					g2.draw(e);
				}
				
				
			}
		});
		
		f.setBounds(100,100,800,800);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
//		Animation<Blendable<? extends Number>> ban = new MyAnimation<Number>();
//		MyAnimation<Number> ban = new MyAnimation<Number>();
//		ban.get(0.3, new Scalar.Double());
		
	}
*/	
}


