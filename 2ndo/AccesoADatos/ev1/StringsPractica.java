/*
** @author 7J
*/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Ex2 {
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		int menu=0;
		while(menu!=3) {
			menu = menu();
			in.nextLine();
			switch (menu){
			case 1:
				ej1();
				break;
			case 2:
				ej2();
				break;
			default:
				break;
			}
		}
		in.close();
	}
	public static int menu() {
		System.out.println("1. Muestra carácteres\n2. Cuenta palabras\n3. Salir");
		while (true) {
			try {
				return in.nextInt();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				in.nextLine();
			}	
		}
	}
	public static void ej1() {
		String s;
		HashSet<Character> chars = new HashSet<Character>();
		System.out.println("Introduce a sentence:");
		s = in.nextLine().replace(" ", "");
		for (char character : s.toCharArray()) {
			if(Character.isLetterOrDigit(character)) {
			chars.add(character);
			}
		}
		System.out.println(chars);
	}
	public static void ej2(){
		String s;
		Map<String, Integer> strings = new HashMap<String, Integer>();
		System.out.println("Introduce a sentence:");
		s = in.nextLine().toLowerCase().replaceAll("[^a-zñ0-9 ']", "");
		for (String word : s.split(" ")){
			if (strings.containsKey(word)) {
				strings.put(word, strings.get(word)+1);
			}else {
				strings.put(word, 1);
			}
		}
		System.out.println(strings);
	}
}
