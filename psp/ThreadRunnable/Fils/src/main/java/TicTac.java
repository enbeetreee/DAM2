
public class TicTac extends Thread{
	static int id=0;
	String tt;
	public TicTac() {
		if (id++%2==0) {
			tt="Tic";
		}else {
			tt="Tac";
		}
	}
	public void run() {
		while (true) {
			System.out.println(tt);
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicTac tic = new TicTac(), tac = new TicTac();
		tic.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tac.start();
	}

}
