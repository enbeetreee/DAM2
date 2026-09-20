package synch;

public class HomeDelSac implements Runnable{
	int xiquets;
	Main n;
	public HomeDelSac(Main n) {
		this.n = n;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (n.nens>0) {
			xiquets = n.agafa(xiquets);
			
		}
		
	}

}
