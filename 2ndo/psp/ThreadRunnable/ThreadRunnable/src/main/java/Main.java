
public class Main {
	public static void main(String[] args) {
		RunnableFil rf = new RunnableFil();
		Thread rfil = new Thread(rf);
		Fil fil = new Fil();
		
		rfil.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fil.start();
		
	}
}
