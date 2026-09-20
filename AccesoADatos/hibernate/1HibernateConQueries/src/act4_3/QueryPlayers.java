package act4_3;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import act4_3.entity.Players;

public class QueryPlayers {
	enum TypeOfStat {
		POINTS("pointsPerMatch"), ASSISTANCES("assistancesPerMatch"), BLOCKS("blocksPerMatch"),
		REBOUND("reboundPerMatch");

		String campo;

		TypeOfStat(String campo) {
			this.campo = campo;
		}
	}

	public static void showPlayers(Players p) {
		System.out.println(p.getName() + ": " + p.getHeight() + ", " + p.getPosition() + ", " + p.getSalary());

	}

	public static Players[] getAllPlayers() {
		Players p[] = null;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {

			Query<Players> q = sess.createQuery("from Players", Players.class);
			List<Players> l = q.list();
			p = l.toArray(new Players[0]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	public static Players getHighestPlayer() {
		Players p = null;

		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {

			Query<Players> q = sess.createQuery("from Players WHERE height = (SELECT MAX(height) from Players)",
					Players.class);
			p = (Players) q.uniqueResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	public static int setSalary(int newSalary, String teamName) {
		int updated = 0;
		Transaction tx = null;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {
			tx = sess.beginTransaction();
			MutationQuery q = sess.createMutationQuery("UPDATE Players SET salary = :salary WHERE teams.name = :team")
					.setParameter("salary", newSalary).setParameter("team", teamName);
			updated = q.executeUpdate();
			tx.commit();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return updated;
	}

	public static int riseSalaryOfMVP(TypeOfStat type, int prctRise, String teamName) {
		int updated = 0;
		Transaction tx = null;
		
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {
			tx = sess.beginTransaction();
			Query<Integer> qCode = sess.createQuery("SELECT p.code FROM Players p, Stats s WHERE p.teams.name = :team AND s.id.player = p.code AND s.id.season = '07/08' AND s."+type.campo+" = "
					+ "(SELECT MAX(s."+type.campo+") FROM Players p, Stats s  WHERE p.teams.name = :team AND s.id.player = p.code AND s.id.season = '07/08')", Integer.class).setParameter("team", teamName);
			MutationQuery q = sess.createMutationQuery("UPDATE Players "
					+ "SET salary = salary + salary * :prct /100 WHERE code = :code")
					.setParameter("prct", (float)prctRise)
					.setParameter("code", qCode.uniqueResult());
			updated = q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}
		return updated;
	}

	public static int deletePlayersOfTeam(String teamName) {
		int updated = 0;
		Transaction tx = null;
		try (Session sess = HibernateUtil.getSessionFactory().openSession()) {
			tx = sess.beginTransaction();
			MutationQuery q = sess.createMutationQuery("DELETE Stats WHERE players.teams.name = :team")
					.setParameter("team", teamName);
			updated = q.executeUpdate();
			q = sess.createMutationQuery("DELETE Players WHERE teams.name = :team")
					.setParameter("team", teamName);
			updated += q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}
		return updated;
	}
}
