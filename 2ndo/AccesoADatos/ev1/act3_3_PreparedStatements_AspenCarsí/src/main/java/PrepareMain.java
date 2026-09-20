import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareMain {

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
		try (Connection con = DriverManager.getConnection("jdbc:hsqldb:./database/")) {//recordar dependencia en pom.xml
			create(con);
			read(con, "departments");
			read(con, "teachers");
			
			test(con);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

	}
	public static void read(Connection con, String table) {
		BufferedReader bf = null;
		File f = new File("./files/"+table+".txt");
		String aux, values[];
		try {
			bf = new BufferedReader(new FileReader(f));
			aux = bf.readLine();
			do {
				values = aux.split(",");
				for (int i = 0; i < values.length; i++) {
					if (values[i].equals("")) {
						values[i]=null;
					}
				}
				insert(con,table,values);
				aux = bf.readLine();
			}while(aux!=null);
			
			bf.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	public static void create(Connection con) {
		try {
			Statement st = con.createStatement();
			String query = "CREATE TABLE departments(dept_num INT PRIMARY KEY,"
					+ "name VARCHAR(30),"
					+ "OFFICE VARCHAR(10))";
			st.executeQuery(query);
			query = "CREATE TABLE teachers("
					+ "id INT PRIMARY KEY,"
					+ "name VARCHAR(20),"
					+ "surname VARCHAR(50),"
					+ "email VARCHAR(60),"
					+ "start_date DATE,"
					+ "dept_num INT,"
					+ "FOREIGN KEY (dept_num) REFERENCES departments(dept_num))";
			st.executeQuery(query);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void insert(Connection con, String tname, String values[]) {
		String sql, aux="(";
		try {
			for (int i = 0; i < values.length; i++) {
				aux+="?,";
			}
			aux = aux.substring(0, aux.length()-1)+")";
			sql = "INSERT INTO "+tname+" VALUES "+aux;
			PreparedStatement sentencia = con.prepareStatement(sql);
			switch (tname) {
			case "teachers":
				if (teachers(sentencia,values)==-1) {
					System.out.println("Error inserting: "+values.toString());
				}else {
					System.out.println(values[1]+" "+values[2]+" inserted succesfully");
				}
				break;
			case "departments":
				if (dpt(sentencia,values)==-1) {
					System.out.println("Error inserting: "+values.toString());
				}else {
					System.out.println(values[1]+" inserted succesfully");
				}
				break;

			default:
				System.out.println("Unknown table");
				break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int dpt(PreparedStatement sentencia, String[] values) {
		int n = 0;
		try {
			sentencia.setInt(n+1, Integer.parseInt(values[n++]));
			sentencia.setString(n+1, values[n++]);
			sentencia.setString(n+1, values[n++]);
			return sentencia.executeUpdate();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return -1;
	}
	public static int teachers(PreparedStatement sentencia, String[] values) {
		int n=0;
		try {
			sentencia.setInt(n+1, Integer.parseInt(values[n++]));
			sentencia.setString(n+1, values[n++]);
			sentencia.setString(n+1, values[n++]);
			sentencia.setString(n+1, values[n++]);
			if (values[n]==null) {
				sentencia.setNull(n+1, java.sql.Types.DATE);
				n++;
			}else {
			sentencia.setDate(n+1, Date.valueOf(values[n++]));}
			sentencia.setInt(n+1, Integer.parseInt(values[n++]));
			return sentencia.executeUpdate();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return -1;
	}
	public static void test(Connection con) {
		try {
			Statement stt = con.createStatement();
			ResultSet rs= stt.executeQuery("SELECT * FROM teachers");
			JDBCHelper.showResultSet(rs);
			rs= stt.executeQuery("SELECT * FROM departments");
			JDBCHelper.showResultSet(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
