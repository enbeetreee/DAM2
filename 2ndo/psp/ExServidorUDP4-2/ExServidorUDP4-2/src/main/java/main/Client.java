package main;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		Cotxe c = creaCotxe();
		DatagramSocket dSocket = null;
		
		try {
			dSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(c);
			byte[] missatgeEixida = outputStream.toByteArray();
			
			
			DatagramPacket dpEixida = new DatagramPacket(missatgeEixida, missatgeEixida.length, aHost, 5000)/* = new DatagramPacket(p.,)*/;
			System.out.println("Connectat");
			dSocket.send(dpEixida);
			System.out.println("Enviat.");
			
			
			
		} catch (SocketException e) {
			System.out.println("Socket: "+ e.getMessage());
		} catch(UnknownHostException e) {
			System.out.println("UnknownHost: "+e.getMessage());
		}catch(IOException e) {
			System.out.println("IO: "+e.getMessage());
		}finally {
			if (dSocket!=null) {
				dSocket.close();
			}
		}

		
		
		
		
	}
	public static Cotxe creaCotxe() {
		boolean cont = false;
		String dni, matricula, marca, model, combustible;
		int any= 0;
		
		System.out.println("Introdueix el DNI del client");
		dni = in.nextLine();
		System.out.println("Introdueix la matricula");
		matricula = in.nextLine();
		System.out.println("Introdueix la marca");
		marca = in.nextLine();
		System.out.println("Introdueix el model");
		model = in.nextLine();
		System.out.println("Introdueix el tipus de combustible");
		combustible = in.nextLine();
		
		while (!cont) {
			try {
				System.out.println("Introdueix l'any de matriculació ");
				any = in.nextInt();
				cont = true;
			} catch (InputMismatchException e) {
				System.out.println("S'esperaba un int\n(...)");
				in.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage()+"\n(...)");
				in.nextLine();
			} finally {
				in.nextLine();
			}
		}
		return new Cotxe(dni, matricula, marca, model, combustible, any);
	}
}
