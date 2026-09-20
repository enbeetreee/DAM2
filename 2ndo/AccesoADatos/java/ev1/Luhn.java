package test1;

import java.util.Scanner;

public class Luhn{

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String auxs="";
		int n=0, auxn=0;
		long cc=0;
		boolean cont = true;
		
		while(cont) {
			try {
				System.out.print("Introduce credit card number: ");
				auxs = in.nextLine().replaceAll(" ","");
				if (auxs.length()!=16) {
					throw new Exception("The number must be 16 digits long");
				}
				cc = Long.parseLong(auxs);
				cont = false;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		auxs = Long.toString(cc);
		for (int i = 0; i < auxs.length()-1; i++) {
			if (i%2==0) {
				auxn=(auxs.charAt(i)-'0')*2;
				if (auxn>9) {
					auxn-=9;
				}
				n+=auxn;
			}else {
				n+=auxs.charAt(i)-'0';
			}
		}

		if(auxs.charAt(15)-'0'==n%10) {
			System.out.println("The card number is correct");
		}else {
			System.out.println("The card number is incorrect");
		}
		in.close();
	}
}
