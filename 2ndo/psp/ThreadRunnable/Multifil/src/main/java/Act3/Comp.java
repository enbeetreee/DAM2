package Act3;

public class Comp {
	private int n=0;

	public Comp() {

	}
	
	public synchronized void llenar(int n) {
		this.n=n;
		notify();
	}
	public synchronized int getN() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
}
