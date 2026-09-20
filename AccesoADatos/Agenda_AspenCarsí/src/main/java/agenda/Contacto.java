package agenda;

import java.io.Serializable;
import java.util.Objects;


/*
** @author Aspen Carsí
*/
public class Contacto implements Serializable, Comparable<Contacto>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String number;
	public Contacto(String name, String surname, String number) {
		this.name = name;
		this.surname = surname;
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
	public int compareTo(Contacto c) {
		if(this.name.equals(c.name)) {
			return this.surname.compareTo(c.surname);
		}
		return this.name.compareTo(c.name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return Objects.equals(name, other.name) && Objects.equals(number, other.number)
				&& Objects.equals(surname, other.surname);
	}

	public String toString() {
		return name +" "+ surname + ", " + number;
	}

	
}
