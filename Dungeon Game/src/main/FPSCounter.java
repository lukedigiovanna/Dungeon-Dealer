package main;

public class FPSCounter extends Thread {
	private double fps;
	
	public void run() {
		while (true) {
			long lastTime = System.nanoTime();
			try {
				Thread.sleep(1000); //this will be interrupted when the frame is over and then the fps will change
			} catch (InterruptedException e) {}
			fps = 1000000000.0/(System.nanoTime()-lastTime);
		}
	}
	
	public double fps() {
		return (int)(fps*10)/10.0;
	}
}
