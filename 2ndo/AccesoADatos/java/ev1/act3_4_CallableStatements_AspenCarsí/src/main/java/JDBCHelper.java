import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCHelper {
	public static void showResultSet(ResultSet res) {
		int maxCol[]= null;
		int nCol;
		int i = 1;
		ArrayList<String[]> a = new ArrayList<String[]>();
		try {
			ResultSetMetaData md = res.getMetaData();
			nCol = md.getColumnCount();
			maxCol = new int[nCol]; 
			a.add(new String[nCol]);
			for (int j = 0; j < nCol; j++) {
				a.get(0)[j]=md.getColumnName(j+1) ;
				maxCol[j] = a.get(0)[j].length()>maxCol[j]?
						a.get(0)[j].length()
						:maxCol[j];
			}
			while (res.next()) {
				a.add(new String[nCol]);
				for (int j = 0; j < nCol; j++) {
					a.get(i)[j] = res.getString(j+1);
					if (a.get(i)[j]==null) {
						a.get(i)[j]="null";
					}
					maxCol[j] = a.get(i)[j].length()>maxCol[j]?
							a.get(i)[j].length()
							:maxCol[j];
				}
				i++;
			}
			/*
			while (res.next()) {
				int c=0;
				a.add(new String[nCol]);
					a.get(0)[0] = md.getColumnName(c+1)+" "+res.getString(c+1);}
				for (int i = 1; i <  nCol; i++) {
					a.get(0)[i] = md.getColumnName(i)+" "+res.getString(i);
					maxCol = a.get(res.getRow()-1)[i].length()>maxCol?a.get(res.getRow()-1)[i].length():maxCol;
				}*/
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------");
		for (String[] strings : a) {
			for (int j = 0; j < strings.length; j++) {
				System.out.print(strings[j]);
				for (int k = 0; k < maxCol[j]-strings[j].length()+3; k++) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	public static boolean containsTable(Connection con, String tableName) {
		try {
			DatabaseMetaData md = con.getMetaData();
			ResultSet resul = md.getTables(null, null, tableName.toUpperCase(), null);
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
