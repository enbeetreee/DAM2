package main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		byte[] missatgeEntrada;
		
		while (true) {
			
			try {
				DatagramSocket dSocket = new DatagramSocket(5000);
				System.out.println("Servidor escoltant al port 5000");
				
				missatgeEntrada = new byte[1024];
				DatagramPacket dpEntrada = new DatagramPacket(missatgeEntrada, missatgeEntrada.length);
				dSocket.receive(dpEntrada);
				System.out.println("Connectat");
				
				byte[] bEntrada = dpEntrada.getData();
				System.out.println("Rebut");
				ByteArrayInputStream in = new ByteArrayInputStream(bEntrada);
				ObjectInputStream ois = new ObjectInputStream(in); 
				
				try {
					Cotxe cotxe = (Cotxe) ois.readObject();
					System.out.println("Objecte rebut vehícle: "+cotxe);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				dSocket.close();//si no explota
			} catch (SocketException e) {
				// TODO: handle exception
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
				
			}
			
		}
	}

}
