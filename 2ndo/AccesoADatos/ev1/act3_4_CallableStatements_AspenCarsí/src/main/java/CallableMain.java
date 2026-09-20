
import java.io.File;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class CallableMain {

	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("./database/");
		if (f.exists()) {
			for (File ff : f.listFiles()) {
				ff.delete();
			}
			f.delete();
		}
		f.mkdir();
		try (Connection con = DriverManager.getConnection("jdbc:hsqldb:./database/")) {// recordar dependencia en
																						// pom.xml
			create(con);
			insert(con);

			createStt(con);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void insert(Connection con) {
		try {
			Statement stt = con.createStatement();
			String sql = "INSERT INTO departments VALUES(10,'INFORMATICA','DESPA6');"
					+ " INSERT INTO departments VALUES(20,'COMERCIO','DESPA7');"
					+ " INSERT INTO departments VALUES(30,'ADMINISTRATIVO','DESPA8');"
					+ " INSERT INTO departments VALUES(40,'FOL','DESPA5');";
			stt.execute(sql);
			sql = "INSERT INTO teachers VALUES(1,'Luz','Martinez','luz.martinez@iesabastos.org','1990-01-01',10);"
					+ " INSERT INTO teachers VALUES(2,'Cristina','Ausina','c.ausina@iesabastos.org','1990-02-01',10);"
					+ " INSERT INTO teachers VALUES(3,'Imma','Cabanes','i.cabanes@iesabastos.org','1990-03-01',10);"
					+ " INSERT INTO teachers VALUES(4,'Mercedes','Sánchez','m.sanchez@iesabastos.org',null,40);";
			stt.execute(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createStt(Connection con) {
		try {
			Statement stt = con.createStatement();

			String sql = "SELECT * FROM departments";
			JDBCHelper.showResultSet(stt.executeQuery(sql));

			// ChangeOffice
			sql = "DROP PROCEDURE changeOffice IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE changeOffice() " + "modifies sql data " + "BEGIN ATOMIC "
					+ "UPDATE departments SET office = 'OFFICE'; " + "END;";
			stt.executeUpdate(sql);

			CallableStatement call = con.prepareCall("call changeOffice()");
			call.execute();

			sql = "SELECT * FROM departments";
			JDBCHelper.showResultSet(stt.executeQuery(sql));

			// changeOffice1
			sql = "DROP PROCEDURE changeOffice1 IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE changeOffice1(newoffice VARCHAR(20)) " + "modifies sql data " + "BEGIN ATOMIC "
					+ "UPDATE departments SET office = newoffice; " + "END; ";
			stt.executeUpdate(sql);

			call = con.prepareCall("call changeOffice1(?)");
			call.setString(1, "OFICINABUENA");
			call.execute();

			sql = "SELECT * FROM departments";
			JDBCHelper.showResultSet(stt.executeQuery(sql));

			// changeOffice3
			sql = "DROP PROCEDURE changeOffice3 IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE changeOffice3(newoffice VARCHAR(20), N VARCHAR(20)) " + "modifies sql data "
					+ "BEGIN ATOMIC "// importante que cada sentencia se cierre con ;
					+ "UPDATE departments SET office = newoffice " + "WHERE name LIKE N; " + "END;";
			stt.executeUpdate(sql);

			call = con.prepareCall("call changeOffice3(?,?)");
			call.setString(1, "BUENAOFI");
			call.setString(2, "%N%");
			call.execute();

			sql = "SELECT * FROM departments";
			JDBCHelper.showResultSet(stt.executeQuery(sql));

			System.out.println("-------------");

			// suma
			sql = "DROP PROCEDURE suma IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE suma (in a int, in b int, out res int) " + "BEGIN ATOMIC " + "set res = a+b;"
					+ "END;";
			stt.executeUpdate(sql);

			call = con.prepareCall("call suma(?,?,?)");
			call.setInt(1, 1);
			call.setInt(2, 1);
			call.registerOutParameter(3, Types.INTEGER);

			call.execute();
			System.out.println("proc: " + call.getInt(3));

			// nextDeptNum
			sql = "DROP PROCEDURE nextDeptNum IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE nextDeptNum (OUT n INT) " + "READS SQL DATA " + "BEGIN ATOMIC "
					+ "SET n = 1+(SELECT MAX(dept_num) from departments); " + "END;";
			stt.executeUpdate(sql);

			call = con.prepareCall("call nextDeptNum(?)");
			call.registerOutParameter(1, Types.INTEGER);

			call.execute();
			System.out.println("Next Dept Num: " + call.getInt(1));

			// TOTAL DEPTS
			sql = "DROP PROCEDURE totalDept IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE totalDept (OUT n INT) " + "READS SQL DATA " + "BEGIN ATOMIC "
					+ "SET n = SELECT COUNT(*) FROM departments; " + "END;";
			stt.executeUpdate(sql);

			call = con.prepareCall("call totalDept(?)");
			call.registerOutParameter(1, Types.INTEGER);

			call.execute();
			System.out.println("Total depts: " + call.getInt(1));

			System.out.println("-------------");

			// DeptId
			sql = "DROP PROCEDURE deptId IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE deptId (IN inName VARCHAR(30), OUT n INT) " + "READS SQL DATA " + "BEGIN ATOMIC "
					+ "SET n = SELECT dept_num FROM departments " + "WHERE inname = UPPER(name); " + "END;";
			stt.executeUpdate(sql);

			call = con.prepareCall("call deptId(?,?)");
			/*
			 * System.out.print("Introduce dept. name: "); call.setString(1,
			 * in.nextLine().toUpperCase()); call.registerOutParameter(2, Types.INTEGER);
			 * 
			 * call.execute(); System.out.println("Dept Id: "+call.getInt(2));
			 */

			// veteran
			sql = "DROP PROCEDURE vetTeacher IF EXISTS";
			stt.executeUpdate(sql);

			sql = "CREATE PROCEDURE vetTeacher (OUT name VARCHAR(30), OUT surname VARCHAR(50)) " + "READS SQL DATA "
					+ "BEGIN ATOMIC " + "SET name = SELECT name FROM teachers "
					+ "WHERE start_date = (SELECT MIN(start_date) FROM teachers); "
					+ "SET surname = SELECT surname FROM teachers "
					+ "WHERE start_date = (SELECT MIN(start_date) FROM teachers); " + "END;";
			stt.executeUpdate(sql);

			call = con.prepareCall("call vetTeacher(?,?)");
			call.registerOutParameter(1, Types.VARCHAR);
			call.registerOutParameter(2, Types.VARCHAR);

			call.execute();
			System.out.println("Most veteran teacher: " + call.getString(1) + " " + call.getString(2));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void create(Connection con) {
		try {
			Statement st = con.createStatement();
			String sql = "DROP TABLE departments IF EXISTS;" + "CREATE TABLE departments(dept_num INT PRIMARY KEY,"
					+ "name VARCHAR(30)," + "office VARCHAR(20));";
			st.execute(sql);
			sql = "DROP TABLE teachers IF EXISTS;" + "CREATE TABLE teachers(" + "id INT PRIMARY KEY,"
					+ "name VARCHAR(20)," + "surname VARCHAR(50)," + "email VARCHAR(60)," + "start_date DATE,"
					+ "dept_num INT," + "FOREIGN KEY (dept_num) REFERENCES departments(dept_num));";
			st.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
