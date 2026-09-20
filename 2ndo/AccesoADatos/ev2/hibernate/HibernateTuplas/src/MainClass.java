import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import entity.Empleados;
import jakarta.persistence.Tuple;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sf = HibernateUtil.getSessionFactory();
		try (Session sess = sf.openSession()){
			Query<Empleados> q = sess.createQuery(
					"FROM Empleados E "
					+ "WHERE E.departamentos.dnombre = 'Ventas' ",
					Empleados.class);
			q.list().forEach(e-> System.out.println(e));
			System.out.println("__________________");
			
			Query<Tuple> q1 = sess.createQuery(
					"SELECT apellido, departamentos.loc "
					+ "FROM Empleados E ",
					Tuple.class);
			q1.list().forEach(t -> 
			System.out.println("Surname: "+t.get(0)+"; City: "+t.get(1)));
			System.out.println("__________________");
			
			Query<Empleados> q2 = sess.createQuery(
					"FROM Empleados E "
					+ "WHERE E.fechaAlta = (SELECT MIN(fechaAlta) FROM Empleados) ",
					Empleados.class);
			
			q2.list().forEach(e -> 
			System.out.println(e));
			System.out.println("__________________");
			
			Query<Tuple> q3 = sess.createQuery(
					"SELECT count(*), E.departamentos.dnombre "
					+ "FROM Empleados E "
					+ "GROUP BY E.departamentos.dnombre",
					Tuple.class);
			
			q3.list().forEach(t -> 
			System.out.println(t.get(0)+" "+t.get(1)));
			System.out.println("__________________");
		} catch (Exception e) {
			throw e;
		}
		
		System.out.println("END");
		sf.close();
	}

}
