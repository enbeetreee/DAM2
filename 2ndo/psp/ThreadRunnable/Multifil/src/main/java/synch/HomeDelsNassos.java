package synch;

public class HomeDelsNassos implements Runnable{
	Main n;
	public HomeDelsNassos(Main n) {
		this.n = n;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (n.nens>0) {
			n.espanta();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
