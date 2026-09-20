package synch2;

public class Sac {
	private boolean espantat = false;
	
	public synchronized void espantar() {
		while (espantat) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Xiquet espantat");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		espantat = true;
		notify();
	}
	public synchronized void agafar() {
		while (!espantat) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Xiquet agafat");
		espantat = false;
		notify();
	}
}
