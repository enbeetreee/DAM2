
public class Act1 extends Thread {
	

	public void run() {

		for (int i = 0; i < 10; i++) {
			System.out.println("El TombaTossals saluda");
			try {

				Act1.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Act1 h = new Act1();
		h.start();

	}

}
