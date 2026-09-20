
import java.util.HashSet;
import java.util.InputMismatchException;

import java.util.Scanner;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import act4_1.entity.*;

//import ex3Hibernate.entity.Libros;

public class MainClass {
	public static Scanner in = new Scanner(System.in);
	public static SessionFactory sf = HibernateUtil.getSessionFactory();

	public static void main(String[] args) {
		int menu = 0;
//		Session ses = sf.openSession();
//		System.out.println("SESSION OPENED");

		while (menu != 11) {
			menu = exInt(
					"1. Show a team by ID\n2. Show a player by ID\n3. Show the players in existing team\n4. Create new team\n5. Create new player with new team associated\n6. Create new player with existing team associated\n7. Delete player\n8. Delete a team\n9. Set salary of all the players of a team\n10. Rise salary for those players of a team who maxed stat in season 07/08\n11. Quit\n");
			switch (menu) {
			case 1:
				showTeam();
				break;
			case 2:
				showPlayer();
				break;
			case 3:
				showPlayersInTeam();
				break;
			case 4:
				createTeam();
				break;
			case 5:
				createPlayerAndTeam();
				break;
			case 6:
				createPlayerInExistingTeam();
				break;
			case 7:
				deletePlayer();
				break;
			case 8:
				deleteTeam();
				break;
			case 9:
				setSalaryOfTeam();
				break;
			case 10:
				riseSalaryOfTeamBestPlayers();
				break;
			case 11:
				System.out.println("Closing");
			default:
				System.out.println("Invalid Option");
			}
			System.out.println("(...)");
			in.nextLine();
		}
	}

	public static Teams getTeam() throws Exception {
		Session ses = sf.openSession();
		System.out.print("Team name: ");
		String name = in.nextLine();
		Teams team = ses.get(Teams.class, name);
		if (team == null) {
			throw new Exception("The team " + name + " doesn't exist");
		}
		return team;
	}

	public static Players getPlayer(int code) throws Exception {
		Session ses = sf.openSession();
		Players pl = ses.get(Players.class, code);
		if (pl == null) {
			throw new Exception("The player " + code + " doesn't exist");
		}
		return pl;
	}

	public static void showTeam() {
		Teams t;
		try {
			t = getTeam();
			System.out.println(t);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}

	}

	public static void showPlayer() {
		try {
			System.out.println(getPlayer(exInt("Player code: ")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void showPlayersInTeam() {
		try {
			Teams team = getTeam();
			team.getPlayerses().forEach(p -> System.out.println("\t"+p));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}

	}

	public static Teams createTeamCon() {
		Teams t = new Teams();
		System.out.print("Name: ");
		t.setName(in.nextLine());
		System.out.print("City: ");
		t.setCity(in.nextLine());
		System.out.print("Conference: ");
		t.setConference(in.nextLine());
		System.out.print("Division: ");
		t.setDivision(in.nextLine());
		return t;
	}
	public static Teams createTeam() {
		Teams t = createTeamCon();
		Session ses = sf.openSession();
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			ses.persist(t);
			tx.commit();
			System.out.println("Team "+t.getName()+" was created");
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}
		return t;
	}

	public static Players createPlayer(Teams t) {
		Players p = new Players();
		p.setCode(exInt("Code:"));
		System.out.print("Name: ");
		p.setName(in.nextLine());
		System.out.print("Origin: ");
		p.setOrigin(in.nextLine());
		System.out.print("Height: ");
		p.setHeight(in.nextLine());
		p.setWeight(exInt("Weight: "));
		System.out.print("Position: ");
		p.setPosition(in.nextLine());
		p.setTeams(t);
		p.setSalary(exInt("Salary: "));
		return p;
	}

	public static void createPlayerAndTeam() {
		Session ses = sf.openSession();
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			System.out.println("Create Team");
			Teams t = createTeamCon();
			System.out.println("Create Player");
			Players p = createPlayer(t);
			ses.persist(t);
			ses.persist(p);
			tx.commit();
			System.out.println("Team "+t.getName()+" & player "+p.getName()+" were created");
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}

	}

	public static void createPlayerInExistingTeam() {
		Session ses = sf.openSession();
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			Teams t = getTeam();
			Players p = createPlayer(t);
			ses.persist(p);
			tx.commit();
			System.out.println("Player "+p.getName()+" was created");
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}

	}

	public static void deletePlayer() {
		Session ses = sf.openSession();
		Transaction tx = null;
		int cod = 0;
		try {
			tx = ses.beginTransaction();
			cod = exInt("Player code: ");
			Players p = ses.get(Players.class, cod);
			in.nextLine();
			if (p == null) {
				throw new Exception("The team " + cod + " doesn't exist");
			}
			p.getStatses().forEach(s -> ses.remove(s));
			ses.remove(p);
			System.out.println("Player " + p.getName() + " was deleted");
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
			// TODO: handle exception7

		}
	}

	public static void deleteTeam() {
		Session ses = sf.openSession();
		Transaction tx = null;
		boolean delp = false;
		try {
			tx = ses.beginTransaction();
			System.out.print("Team name: ");
			String name = in.nextLine();
			Teams t = ses.get(Teams.class, name);
			if (t == null) {
				throw new Exception("The team " + name + " doesn't exist");
			}
			if (!t.getPlayerses().isEmpty()) {
				System.out.println(
						"The team can't be deleted while it still has registered players\nDelete players and their stats? (y/n)");
				if (in.nextLine().toLowerCase().equals("y")) {
					t.getPlayerses().forEach(p -> {
						p.getStatses().forEach(s -> ses.remove(s));
						ses.remove(p);
					});
					t.getMatchesesForLocalTeam().forEach(m -> ses.remove(m));
					t.getMatchesesForVisitorTeam().forEach(m -> ses.remove(m));
				} else {
					throw new Exception("Team couldn't be deleted");
				}
			}
			ses.remove(t);
			tx.commit();
			System.out
					.println("Team " + t.getName() + (delp == true ? " and its players were deleted" : " was deleted"));
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
	}

	public static void setSalaryOfTeam() {
		Session ses = sf.openSession();
		Transaction tx = null;
		int sal;
		try {
			tx = ses.beginTransaction();
			System.out.print("Team name: ");
			String name = in.nextLine();
			Teams t = ses.get(Teams.class, name);

			if (t == null) {
				throw new Exception("The team " + name + " doesn't exist");
			}

			sal = exInt("New salary: ");
			for (Players p : t.getPlayerses()) {
				p.setSalary(sal);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}

	}

	public static void riseSalaryOfTeamBestPlayers() {
		Session ses = sf.openSession();
		Transaction tx = null;
		String raux;
		boolean cont = false;
		float max=0;
		int rise=0;
		Stats s;
		int menu = -1;
		HashSet<Players> players = new HashSet<Players>();
		try {
			tx = ses.beginTransaction();
			System.out.print("Team name: ");
			String name = in.nextLine();
			Teams t = ses.get(Teams.class, name);
			if (t == null) {
				throw new Exception("The team " + name + " doesn't exist");
			}
			while (!cont) {
				try {
					System.out.print("Percentage to rise (p.e: 50%): ");
					raux = in.nextLine();
					if (raux.charAt(raux.length()-1)=='%') {
						raux = raux.substring(0, raux.length()-1);
					}
					rise = Integer.parseInt(raux);
					cont = true;
				} catch (Exception e) {
					System.out.println("Unexpected format");
				}

			}
			while (menu < 1 || menu > 4) {
				menu = exInt(
						"Over which stat:\n1. Points per match\n2. Assistances per match\n3. Blocks per match\n4. Rebound per match\n");
			}
			for (Players p : t.getPlayerses()) {
				s = ses.get(Stats.class, new StatsId("07/08", p.getCode()));
				switch (menu) {
				case 1: 
					if (s.getPointsPerMatch()>max) {
						max = s.getPointsPerMatch();
					}
					break;
				case 2:
					if(s.getAssistancesPerMatch()>max) {
						max = s.getAssistancesPerMatch();
					}
					break;
				case 3:
					if(s.getBlocksPerMatch()>max) {
						max = s.getBlocksPerMatch();
					}
					break;
				case 4:
					if(s.getReboundPerMatch()>max) {
						max = s.getReboundPerMatch();
					}
					break;
				}
			}
			for (Players p : t.getPlayerses()) {
				s = ses.get(Stats.class, new StatsId("07/08", p.getCode()));
				switch (menu) {
				case 1: 
					if (s.getPointsPerMatch()==max) {
						players.add(p);
					}
					break;
				case 2:
					if(s.getAssistancesPerMatch()==max) {
						players.add(p);
					}
					break;
				case 3:
					if(s.getBlocksPerMatch()==max) {
						players.add(p);
					}
					break;
				case 4:
					if(s.getReboundPerMatch()==max) {
						players.add(p);
					}
					break;
				}
			}
			for (Players p : players) {
				p.setSalary(p.getSalary()+p.getSalary()*rise/100);
				System.out.println(p.getName()+(p.getName().charAt(p.getName().length()-1)=='s'?"'":"'s")+" rose to "+p.getSalary()+"$");
			}
			tx.commit();
			System.out.println();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e.getMessage());
		}

	}

	public static int exInt(String q) {
		while (true) {
			try {
				System.out.print(q);
				return in.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("An integer was expected\n(...)");
				in.nextLine();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				in.nextLine();
			}
		}
	}

}
