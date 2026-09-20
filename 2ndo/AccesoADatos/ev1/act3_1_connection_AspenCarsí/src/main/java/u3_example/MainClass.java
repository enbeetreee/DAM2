package u3_example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
** @author Aspen Carsí
*/
public class MainClass {
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		boolean cont;
		int menu = 0;
		while (menu != 3) {
			cont = false;
			while (!cont) {
				try {
					menu = menu();
					cont = true;
				} catch (InputMismatchException e) {
					System.out.println("Error: InputMismatchException\n");
					cont = false;
				} finally {
					in.nextLine();
				}
			}
			switch (menu) {
			case 1:
				// sqlite
				connect("sqlite:./sqlite.db");
				break;
			case 2:
				// hsqldb
				connect("hsqldb:./hsqldb/nombre_DB");
				break;
			case 3:
				System.out.println("Exiting...");
			default:
				System.out.println("Unexpected value");
			}
			System.out.println("(...)");
			in.nextLine();
		}

	}

	public static int menu() {
		System.out.println("Choose the database:\n1. SQLite Database\n2. HSQLDB Database\n3. Exit");
		return in.nextInt();
	}

	public static void connect(String url) {
		try (Connection con = DriverManager.getConnection("jdbc:"+url)) {// para evitar try catch .close
			Statement stt = con.createStatement();
			String query = "SELECT * FROM teachers;";
			ResultSet rs = stt.executeQuery(query);
			JDBCHelper.showResultSet(rs);
			
			DatabaseMetaData dbmd = con.getMetaData();
						System.out.println("----------------------------------\n" + "DATABASE INFORMATION\n"
					+ "----------------------------------");
			System.out.println("Name: " + dbmd.getDatabaseProductName());
			System.out.println("Driver: " + dbmd.getDriverName());
			System.out.println("URL: " + dbmd.getURL());
			System.out.println("User: " + dbmd.getUserName());

			System.out.println("----------------------------------\n" + "TABLES INFORMATION\n"
					+ "----------------------------------");
			ResultSet resul = dbmd.getTables(//all tables in sql, only public in hsqldb
					null, (url.contains("hsqldb")?"PUBLIC":null), null, null); 
			JDBCHelper.showResultSet(resul);
			 
			while (resul.next()) {
				String catalog = resul.getString(1); // column 1: TABLE_CAT
				String schema = resul.getString(2); // column 2: TABLE_SCHEM
				String name = resul.getString(3); // column 3: TABLE_NAME
				String type = resul.getString(4); // column 4: TABLE_TYPE

				System.out.println(
						"TABLE NAME: " + name + "; Catalog: " + catalog + "; Schema: " + schema + "; Type: " + type);

				System.out.println("*** COLUMNS of TABLE " + name + " ***");
				ResultSet columnas = dbmd.getColumns(null, null, name, null);

				while (columnas.next()) {
					String colName = columnas.getString("COLUMN_NAME");
					String colType = columnas.getString("TYPE_NAME");
					String colSize = columnas.getString("COLUMN_SIZE");
					String nula = columnas.getString("IS_NULLABLE");

					System.out.println(
							"Column name: " + colName + "; Type: " + colType
							+(colType.equals("VARCHAR")?"("+colSize+")":"") 
							+ "; IsNullable: " + nula);
				}
				System.out.println("----------------------------------");

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static boolean containsTable(Connection con, String tableName) {
		try {
			DatabaseMetaData md = con.getMetaData();
			ResultSet resul = md.getTables(null, null, tableName, null);
			if (resul.next()) {
				return true;			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
