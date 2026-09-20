package act4_3;

import java.util.HashMap;
import java.util.InputMismatchException;

import java.util.Scanner;

import act4_3.QueryPlayers.TypeOfStat;
import act4_3.entity.Players;
import act4_3.entity.Teams;

public class Main {
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int menu = 0;
		while (menu != 10) {
			menu = exInt("1. Show all Teams\n2. Show Team whose name matches a pattern\n3. Get average salary of a Team (by Name)\n4. Show average salary of each Team\n5. Show all Players\n6. Show highest Player\n7. Set salary of all Players of a Team\n8. Rise salary of MVP of a Team in 07/08 season\n9. Delete ALL Players of a Team\n10. Quit\n");
			switch (menu) {
			case 1:
				showTeams();
				break;
			case 2:
				matchesPattern();
				break;
			case 3:
				avgTeamName();				
				break;
			case 4:
				avgTeams();
				break;
			case 5:
				showPlayers();
				break;
			case 6:
				showHighestPlayer();
				break;
			case 7:
				setSalary();
				break;
			case 8:
				riseMVP();
				break;
			case 9:
				deletePlayers();
				break;
			case 10:
				break;
			default:
				System.out.println("Unexpected value");
			}
			System.out.println("(...)");
			in.nextLine();

		}
	}

	public static void showTeams() {
		for (Teams t : QueryTeams.getAllTeams()) {
			QueryTeams.showTeam(t);
		}
	}

	public static void matchesPattern() {
		QueryTeams.showTeam(QueryTeams.getTeamByName(sLength("Team name: ",20)));
		
	}

	public static void avgTeamName() {
		String name = sLength("Team name: ",20);
		System.out.println(name+": "+QueryTeams.getAverageSalaryofTeam(name));

	}

	public static void avgTeams() {
		HashMap<String, Double> map = QueryTeams.getAverageSalaryPerTeam();
		map.keySet().forEach(k -> System.out.println(k+": "+map.get(k)));
	}

	public static void showPlayers() {
		for (Players p : QueryPlayers.getAllPlayers()) {
			QueryPlayers.showPlayers(p);
		}
		
	}

	public static void showHighestPlayer() {
		QueryPlayers.showPlayers(QueryPlayers.getHighestPlayer());

	}

	public static void setSalary() {
		System.out.println(QueryPlayers.setSalary(exInt("Salary: "),sLength("Team name: ",20))+" columns updated");

	}

	public static void riseMVP() {
		//NO VA
		TypeOfStat type;
		boolean cont = false;
		String raux;
		int prct= 0;

		String team = sLength("Team name: ",20);
		switch (exInt("Over:\n1. Point per match\n2. Assistances per match\n3. Blocks per match\n4. Rebound per match\n")) {
		case 1: 
			type = TypeOfStat.POINTS;
			break;
		case 2:
			type = TypeOfStat.ASSISTANCES;
			break;
		case 3:
			type = TypeOfStat.BLOCKS;
			break;
		case 4:
			type = TypeOfStat.REBOUND;
			break;
		default:
			System.out.println("Unexpected value");
			return;
		}
		while (!cont) {
			try {
				System.out.print("Percentage to rise (p.e: 50%): ");
				raux = in.nextLine();
				if (raux.charAt(raux.length()-1)=='%') {
					raux = raux.substring(0, raux.length()-1);
				}
				prct = Integer.parseInt(raux);
				cont = true;
			} catch (Exception e) {
				System.out.println("Unexpected format");
			}

		}
		System.out.println(QueryPlayers.riseSalaryOfMVP(type, prct, team)+" columns updated");
		
	}

	public static void deletePlayers() {
		String team = sLength("Team name: ",20);
		System.out.println(QueryPlayers.deletePlayersOfTeam(team)+" columns updated");
	}

	public static int exInt(String s) {
		while (true) {
			try {
				System.out.print(s);
				return in.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Error: An Integer was expected");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				in.nextLine();
			}
		}
	}

	public static String sLength(String q, int n) {// comprueba que el String a introducir no supera limite permitido
		String s = "";
		while (true) {
			try {
				System.out.print(q);
				s = in.nextLine();
				if (s.length() > n) {
					throw new Exception("No se aceptan valores de más de " + n + " carácteres");
				}
				return s;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
