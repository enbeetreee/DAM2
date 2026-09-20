package main;

import java.io.Serializable;

public class Cotxe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String DNIclient, matricula, marca, model, combustible;
	private int any;
	public Cotxe(String dNIclient, String matricula, String marca, String model, String combustible, int any) {
		super();
		DNIclient = dNIclient;
		this.matricula = matricula;
		this.marca = marca;
		this.model = model;
		this.combustible = combustible;
		this.any = any;
	}
	@Override
	public String toString() {
		return "DNI: " + DNIclient + ", Matrícula: " + matricula + ", Marca: " + marca + ", Model: " + model
				+ ", Combustible: " + combustible + ", Any:" + any;
	}
}
