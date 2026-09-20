package agenda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import com.thoughtworks.xstream.XStream;

/*
** @author Aspen Carsí
*/

public class MainAgenda {
	public static Scanner in = new Scanner(System.in);
	public static XStream flujox = new XStream();
	public static File fxml = new File("Agenda.xml");
	public static File fbin = new File("Agenda");
	//public static File fjson = new File("AgendaJson");


	public static void main(String[] args) {
		TreeSet<Contacto> agenda;
		int menu = 0;
		agenda = read();//lee todos los ficheros asociados al programa
		while (menu != 3) {
			try {
				menu = menu();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			in.nextLine();
			switch (menu) {
			case 1:
				showContacts(agenda);
				break;
			case 2:
				agenda.add(addContact());
				break;
			case 3:
				write(agenda);
				break;
			default:
				break;
			}
		}
		in.close();
	}

	public static int menu() {
		System.out.println("1. Show all contacts\n2. Add new contact\n3. Save and exit");
		return in.nextInt();
	}

	public static void showContacts(TreeSet<Contacto> agenda) {
		int i=0;
		System.out.println("Contactos:");
		for (Contacto contacto : agenda) {
			System.out.println("\t"+(i++)+". "+contacto.toString());
		}
	}

	public static Contacto addContact() {
		String name, sname, number;
		System.out.print("Name: ");
		name = in.nextLine();
		System.out.print("Surname: ");
		sname = in.nextLine();
		System.out.print("Number: ");
		number = in.nextLine();
		return new Contacto(name, sname, number);
	}

	public static TreeSet<Contacto> read() {
		TreeSet<Contacto> agenda = new TreeSet<Contacto>();
		ArrayList<Contacto> aux;// treeSet no puede leerse en xml, 
		if (fxml.exists()) {
			flujox.allowTypes(new Class[] { Contacto.class });
			aux = (ArrayList<Contacto>) flujox.fromXML(fxml);
			for (Contacto contacto : aux) {
				agenda.add(contacto);
			}
		}
		if (fbin.exists()) {
			aux = readbin();
			for (Contacto contacto : aux) {
				agenda.add(contacto);
			}

		}
		return agenda;
	}

	public static ArrayList<Contacto> readbin() {
		ObjectInputStream ois = null;
		ArrayList<Contacto> aux = new ArrayList<Contacto>();
		try {
			ois = new ObjectInputStream(new FileInputStream(fbin));
			aux = (ArrayList<Contacto>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return aux;

	}

	public static void write(TreeSet<Contacto> agenda) {
		int n = 0;
		boolean cont = false;
		ArrayList<Contacto> aux = toArrayList(agenda);
		while (!cont) {
			cont = true;
			try {
				System.out.println("Save in XML (1), binary file (2)");
				n = in.nextInt();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			in.nextLine();
			switch (n) {
			case 1:
				writeXml(aux);
				break;
			case 2:
				writeBin(aux);
				break;
			default:
				cont = false;
				break;
			}

		}

	}
	public static ArrayList<Contacto> toArrayList(TreeSet<Contacto> agenda) {
		ArrayList<Contacto> aux = new ArrayList<Contacto>();
		for (Contacto contacto : agenda) {
			aux.add(contacto);
		}
		return aux;
	}
	
	public static void writeXml(ArrayList<Contacto> aux) {
		try {
			flujox.toXML(aux, new FileOutputStream(fxml));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void writeBin(ArrayList<Contacto> aux) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fbin));
			oos.writeObject(aux);
			oos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


}
