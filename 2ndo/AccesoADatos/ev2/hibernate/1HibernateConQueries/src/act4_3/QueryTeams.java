package act4_3;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import act4_3.entity.Teams;
import jakarta.persistence.Tuple;

public class QueryTeams {
	public static void showTeam(Teams t) {
		Teams tPers = null;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {
			tPers = sess.merge(t);
			System.out.println(t.getName() + ": " + t.getCity() + ", " + t.getConference() + ", " + t.getDivision() + ", "
					+ tPers.getPlayerses().size() + " players");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

	public static Teams[] getAllTeams() {
		Teams[] t = null;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {
			Query<Teams> q = sess.createQuery("FROM Teams", Teams.class);
			List<Teams> l = q.list();
			t = l.toArray(new Teams[0]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return t;
	}

	public static Teams getTeamByName(String patternName) {
		Teams t = null;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {

			Query<Teams> q = sess.createQuery("FROM Teams WHERE name = :name", Teams.class);
			q.setParameter("name", patternName);
			t = (Teams) q.uniqueResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return t;
	}

	public static double getAverageSalaryofTeam(String depName) {
		double avg = 0;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {

			Query<Double> q = sess.createQuery("SELECT AVG(salary) FROM Players WHERE teams.name = :name", Double.class);
			q.setParameter("name", depName);
			avg = q.uniqueResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return avg;
	}

	public static HashMap<String, Double> getAverageSalaryPerTeam() {
		HashMap<String, Double> salarios = new HashMap<String, Double>();
		Tuple tuples[];
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {

			Query<Tuple> q = sess.createQuery("SELECT teams.name, AVG(salary) FROM Players GROUP BY teams.name",
					Tuple.class);
			tuples =  q.list().toArray(new Tuple[0]);
			for (Tuple tup : tuples) {
				salarios.put(tup.get(0, String.class), tup.get(1, Double.class));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return salarios;
	}
}
