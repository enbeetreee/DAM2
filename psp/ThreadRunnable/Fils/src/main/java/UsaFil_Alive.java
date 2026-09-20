
public class UsaFil_Alive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FilExemple1 h = new FilExemple1(0);
		
		System.out.println("Abans cridada a start");
		System.out.println("Està viu = "+h.isAlive());
		System.out.println("Estat: "+ h.getState());
		
		System.out.println("Cride a start");
		h.start();
		
		System.out.println("Estat: "+h.getState());
		System.out.println("Està viu?? = "+h.isAlive());
		
		try {
			h.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
