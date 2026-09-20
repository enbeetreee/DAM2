/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author 7JDAM
 */
public class Contact implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name, surname, telf;

    public Contact(String name, String surname, String telf) {
        this.name = name;
        this.surname = surname;
        this.telf = telf;
    }
    public Contact() {

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

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
	}

	@Override
    public String toString() {
        return this.name + " " + this.surname +"; " + this.telf ;
    }

    
}
