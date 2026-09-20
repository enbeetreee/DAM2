package synch;

public class Main {
	int nens = 10;
	public synchronized void espanta() {
		System.out.println("L'home dels nassos ha espantat un xiquet");
		notify();
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public synchronized int agafa(int xiquets) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notify();
			
			nens--;
			System.out.println("L'home del sac ha agafat un xiquet. Hi tenen "+(++xiquets)+" xiquets");
			return xiquets;
	}
	public static void main(String[] args) {
		Main m = new Main();
		HomeDelsNassos hn = new HomeDelsNassos(m);
		HomeDelSac hs = new HomeDelSac(m);
		Thread f1 = new Thread(hs), f2 = new Thread(hn);
		f1.start();
		f2.start();
		
		
	}
}
