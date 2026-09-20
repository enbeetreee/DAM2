package ex1Hibernate;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;

import ex1Hibernate.entity.Departamentos;
import ex1Hibernate.entity.Empleados;
import exemploDept.HibernateUtil;


public class MainClass {
	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		if (session != null) {
			System.out.println("Opened Session");
		} else {
			System.out.println("Error opening session");
		}
		showDepts(session);
		System.out.println("END");
	}
	public static void showDepts(Session session) {
		
			Query<Departamentos> q = session.createQuery("from Departamentos", Departamentos.class);
			List<Departamentos> results = q.getResultList();

			System.out.println("Showing departments data: ");

			for (Departamentos result : results) {
				System.out.println(result.getDeptNo() + ": " + result.getDnombre() + ", in "
						+ result.getLoc());
				for(Empleados e: result.getEmpleadoses()) {
					System.out.println("\t"+e.getEmpNo()+": "+e.getApellido());
				}
			}
		
	}
}
