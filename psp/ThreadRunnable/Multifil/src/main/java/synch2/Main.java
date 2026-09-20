package synch2;

public class Main {
	public static void main(String[] args) {
		Sac sac = new Sac();
		Hds hds = new Hds(sac);
		Mm mm = new Mm(sac);
		hds.start();
		mm.start();
	}
	
}
