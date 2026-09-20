package ex2Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;


public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		if (session != null) {
			System.out.println("Opened Session");
		} else {
			System.out.println("Error opening session");
		}
		System.out.println("END");
	}

}
