package Act3;

public class HomeDelsNassos extends Thread{
	private Comp comp;
	public HomeDelsNassos(Comp comp) {
		this.comp = comp;
	}
	
	public void run() {
		System.out.println(comp.getN());
		
	}
}
