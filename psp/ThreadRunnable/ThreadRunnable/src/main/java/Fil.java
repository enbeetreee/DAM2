
public class Fil extends Thread{
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Fil: "+i);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
