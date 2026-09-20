package ex3Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

//import ex3Hibernate.entity.Libros;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		
		Transaction tx = null;

		try {
			tx = ses.beginTransaction();
			//Libros l = new Libros(); // -> Transcient
			//asocia l a la sesión -> Persistent
			// cierro la sesión -> Detached
			//transaccioneación
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(tx != null) {
				tx.rollback();
			}
			throw e;
		}
		
		System.out.println("END");
	}

}
