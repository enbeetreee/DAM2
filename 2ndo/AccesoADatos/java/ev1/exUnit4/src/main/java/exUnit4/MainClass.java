package exUnit4;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;

import exUnit4.entity.Authors;
import exUnit4.entity.Books;

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

		showBooks(session);
		showAuthors(session);

		System.out.println("END");
	}

	public static void showBooks(Session session) {
		Query<Books> q = session.createQuery("from Books", Books.class);
		List<Books> results = q.getResultList();
		System.out.println("Showing books data: ");

		for (Books result : results) {
			System.out.println(result.getId() + ": " + result.getTitle() + ", by "
					+ (result.getAuthors() != null ? result.getAuthors().getName() : "Anónimo"));
		}
	}

	public static void showAuthors(Session session) {
		Query<Authors> q = session.createQuery("from Authors", Authors.class);
		List<Authors> results = q.list();
		System.out.println("Showing Authors data: ");

		for (Authors result : results) {
			System.out.println("The author " + result.getCod() + " with name " + result.getName() + " ha escrito :");
			result.getBookses().forEach(e -> System.out.println("\t*" + e.getTitle()));
		}
	}

}
