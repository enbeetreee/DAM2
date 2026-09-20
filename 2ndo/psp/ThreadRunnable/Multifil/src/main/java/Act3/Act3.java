package Act3;


public class Act3 extends Thread {
	
	public static void main(String[] args) {
		Comp comp = new Comp();
		Buitoni buitoni = new Buitoni(comp);
		HomeDelsNassos homeDelsNassos = new HomeDelsNassos(comp);
		
		buitoni.start();
		homeDelsNassos.start();
		
		

		

	}
}
