package synch2;

public class Mm extends Thread{
	private Sac sac;

	public Mm(Sac sac) {
		super();
		this.sac = sac;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			sac.espantar();
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
