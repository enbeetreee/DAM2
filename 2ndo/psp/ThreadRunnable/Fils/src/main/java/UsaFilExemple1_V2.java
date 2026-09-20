
public class UsaFilExemple1_V2 extends Thread {
	
	public static void main(String[] args) {
		FilExemple1 h = null;
		for (int i = 0; i < 3; i++) {
			h = new FilExemple1(i+1);
			h.start();//inicializa run()
			System.out.println("status = "+h.isAlive());
		}
		System.out.println("3 fils creats...");
	}
}
