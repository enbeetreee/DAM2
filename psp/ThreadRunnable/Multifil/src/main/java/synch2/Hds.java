package synch2;

public class Hds extends Thread{
	private Sac sac;

	public Hds(Sac sac) {
		super();
		this.sac = sac;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			sac.agafar();
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
