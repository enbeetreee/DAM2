package u3_example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
** @author Aspen Carsí
*/
public class HSQLDBconection {
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		int menu = 0;

		try (Connection con = DriverManager.getConnection("jdbc:hsqldb:./hsqldbsports/")) {//hacer conexión en cada método, no pasar como var
			if (!JDBCHelper.containsTable(con, "PLAYERS") && !JDBCHelper.containsTable(con, "SPORTS")) {
				create(con);
			}
			while (menu != 5) {
				try {
					menu = menu();
				} catch (InputMismatchException e) {
					// TODO: handle exception
				} finally {
					in.nextLine();
				}
				switch (menu) {
				case 1:
					addSport(con);
					break;
				case 2:
					addPlayer(con);
					break;
				case 3:
					showPlayers(con);
					break;
				case 4:
					delSport(con);
					break;
				case 5:
					System.out.println("Bye!");
					break;
				default:
					System.out.println("Invalid option");
					break;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addSport(Connection con) {
		int code = 0;
		String name;
		boolean cont = false;
		while (!cont) {
			try {
				System.out.println("Introduce code: ");
				code = in.nextInt();
				cont = true;
			} catch (InputMismatchException e) {
				System.out.println("Code must be an integer");
			} finally {
				in.nextLine();
			}
			System.out.println("Introduce name: ");
			name = in.nextLine().toLowerCase();
			try{
				Statement stt = con.createStatement();
				String query = "INSERT INTO sports VALUES(" + code + ",'" + name + "')";
				stt.executeQuery(query);
				System.out.println("Sport added succesfully");

			} catch (SQLException e) {
				e.printStackTrace(); // TODO: handle exception
			}

		}

	}

	public static void addPlayer(Connection con) {
		int pcode = 0;
		String name, sname;
		boolean cont = false;
		while (!cont) {
			try {
				System.out.println("Introduce player code: ");
				pcode = in.nextInt();
				cont = true;
			} catch (InputMismatchException e) {
				System.out.println("Code must be an integer");
			} finally {
				in.nextLine();
			}
		}
			System.out.println("Introduce sport name: ");
			sname = in.nextLine();
			System.out.println("Introduce name: ");
			name = in.nextLine();
			try{
				Statement stt = con.createStatement();
//				String query = "SELECT s.cod FROM sports s WHERE LOWER(s.name) = '" +sname.toLowerCase()+"'";
//				ResultSet rs =stt.executeQuery(query);
				String query = "INSERT INTO players VALUES(" + pcode + ", '" + name + "'," 
						+"(SELECT s.cod FROM sports s WHERE LOWER(s.name) = '" +sname.toLowerCase()+"')" +")";
				stt.executeQuery(query);
				System.out.println("Player added succesfully");

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		

	}

	public static void showPlayers(Connection con) {
		String sname;
		System.out.println("Insert sport name: ");
		sname = in.nextLine();
		try{
			Statement stt = con.createStatement();
			String query = "SELECT p.cod,p.name, s.name sport from players p, sports s" + " WHERE p.cod_sport = s.cod AND LOWER(s.name) = '"
					+ sname.toLowerCase()+"'";
			ResultSet rs =stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	public static void delSport(Connection con) {
		String sname;
		System.out.println("Insert sport name: ");
		sname = in.nextLine();
		try{
			Statement stt = con.createStatement();
			String query = "DELETE FROM sports" + " WHERE LOWER(name) = \'" + sname.toLowerCase()+"\'";
			stt.executeQuery(query);
			System.out.println(sname+" succesfully deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public static int menu() {
		System.out.println(
				"1. Add sport\n2. Add player\n3. Show names of player in a sport\n4. Delete sport and all associated players");
		return in.nextInt();
	}

	public static void create(Connection con) {
		try{
			Statement stt = con.createStatement();
			String query = "CREATE TABLE players(" + "cod INT PRIMARY KEY," + "name VARCHAR(50)," + "cod_sport INT);";
			stt.executeQuery(query);
			query = "CREATE TABLE sports(" + "cod INT PRIMARY KEY," + "name VARCHAR(20) UNIQUE);";
			stt.executeQuery(query);
			query = "ALTER TABLE players" + " ADD CONSTRAINT FK_cod_sport FOREIGN KEY (cod_sport)"
					+ " REFERENCES sports(cod)" + " ON DELETE CASCADE;";
			stt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
