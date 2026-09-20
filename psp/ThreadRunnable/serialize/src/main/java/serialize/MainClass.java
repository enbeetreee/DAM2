package serialize;

import com.thoughtworks.xstream.XStream;
import java.io.*;

/*
** @author Aspen Carsí
*/
public class MainClass {

	public static void main(String[] args) {
		Persona p = new Persona("Pepe",20);
		// Create XStream and allow types to use
		XStream flujox = new XStream();//dependency has to be added in .pom
		flujox.allowTypes(new Class[]{Persona.class});

		//Serialize to XML
		try {
			flujox.toXML(p, new FileOutputStream("PersonsInfo.xml"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		//DeSerialize from XML
		Persona[] newArray= (Persona[]) flujox.fromXML(new File("PersonsInfo.xml"));//importante castear
		System.out.println("Persona leída: "+newArray);

	}

}
