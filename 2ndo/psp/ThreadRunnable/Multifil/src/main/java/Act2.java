

public class Act2 extends Thread{
	public static int n=1;
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println(n++);
			try {
				Act2.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Act2 banyeta = new Act2();
		Act2 buitoni = new Act2();
		
		try {
			banyeta.start();
			banyeta.join();
			buitoni.start();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
