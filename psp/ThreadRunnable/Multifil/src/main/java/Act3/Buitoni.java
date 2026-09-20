package Act3;

public class Buitoni extends Thread{
	private Comp comp;
	public Buitoni(Comp comp) {
		this.comp = comp;
	}

	public void run() {
		comp.llenar(((int)Math.floor(Math.random()*10)));
	}

}
