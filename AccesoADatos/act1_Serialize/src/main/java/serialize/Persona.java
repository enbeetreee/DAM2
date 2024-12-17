package serialize;

import java.io.Serializable;
import java.util.Objects;

/*
** @author Aspen Carsí
*/
public class Persona implements Serializable{
	private String name;
	private int edad;

	public Persona(String name, int edad) {
		this.edad = edad;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(edad, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return edad == other.edad && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", edad=" + edad + "]";
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
