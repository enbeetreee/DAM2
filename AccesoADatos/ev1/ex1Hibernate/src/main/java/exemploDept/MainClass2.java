package exemploDept;


import java.sql.Date;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import ex1Hibernate.entity.*;

public class MainClass2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		
		insertEmp(ses);
		

		
		sf.close();
	}
	
	public static void insertEmp(Session ses) {
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			Departamentos dep = ses.get(Departamentos.class, 12);
			Empleados emp = new Empleados(101, dep, "Garciaaaaa", "Oficina", 2, Date.valueOf("2023-12-1"), 1500f,10f);
			//ses.remove(dep);
			ses.persist(emp);			
			tx.commit(); //-> persistente
		} catch (Exception e) {
			// TODO: handle exception
			if(tx != null) {
				tx.rollback();
			}
			throw e;
		}
	}
	public static void userInput(Session ses) {
		
	}
}
