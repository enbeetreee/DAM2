/*
 ** @author Aspen Carsí
 */
package dictionary;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.TreeSet;

import java.util.Scanner;

public class Dictionary {
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		TreeSet<String> tree = new TreeSet<String>();
		mkdir();
//		System.out.println("Read file? (y/n)");
//		if (in.nextLine().equals("y")) {
			read(tree);
//		}
		tree = readTree(tree);
		write(tree);

	}
	public static void mkdir() {//carpeta
		File f = new File("./Dictionary");
		try {
			f.mkdir();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void read(TreeSet<String> tree) {//lee archivo y añade cada palabra al TreeSet
		File r;
		FileReader fr;
		BufferedReader br = null;
		String s = "";
		System.out.println("File to read:");
		r = new File(in.nextLine());
		try {
			fr = new FileReader(r);
			br = new BufferedReader(fr);
			while (s != null) {
				s = br.readLine();
				if (s!=null) {
					s = s.toLowerCase().replaceAll("[^a-z0-9 ]", "");
					for (String string : s.split(" ")) {
						tree.add(string);
					}
				}	
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static TreeSet<String> readTree(TreeSet<String> tree) {//lee archivos previos para mantener el orden de los ficheros
		File folder = new File("./Dictionary/");
		File fileNames[] = folder.listFiles();
		BufferedReader br;
		String s="";
		for (File file : fileNames) {
			try {
				br = new BufferedReader(new FileReader(file));
				while(s!=null) {
					s = br.readLine();
					if (s!=null) {
						tree.add(s);
					}
				}
				br.close();
			}catch(Exception e){
				
			}
		}
		return tree;
	}
	public static void write(TreeSet<String> tree) {//crea o reescribe los ficheros
		File f = new File("");
		BufferedWriter bw=null;
		char letra='.';
		for (String string : tree) {
			if(letra != string.charAt(0)&&letra!=' ') {
				letra = string.charAt(0);
				try {
					if (bw!=null) {
						bw.close();
					}
					f = new File("./Dictionary/"+letra+".txt");
					bw = new BufferedWriter(new FileWriter(f));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
			try {
				bw.write(string.substring(0,1).toUpperCase()+string.substring(1)+"\n");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		System.out.println("Operation completed succesfully");
		try {
			bw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
