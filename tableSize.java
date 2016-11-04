package informationRetrievalAssignment;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class tableSize {

	public void findDbSpace() throws ClassNotFoundException, SQLException{
		
		Connection c = null;
		Statement stmt = null;
		ResultSet rs;
		Class.forName("com.mysql.jdbc.Driver");
		
		//Connecting to mysql
		c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "titli123");
		
		//Getting the table size
		//String query = "SELECT table_name AS `sizeTable`, round(((data_length + index_length) / 1024 / 1024), 2) `Size in MB` FROM information_schema.TABLES WHERE table_schema = '$test' AND table_name = '$dbtuple'";
		String sql = "SELECT table_schema AS \"Database\", "
		        + "ROUND(SUM(data_length + index_length) / 1024, 2) AS \"Size (KB)\" "
		        + "FROM information_schema.TABLES GROUP BY table_schema;";		
		PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String dbname =  rs.getString("Database");
			String size = rs.getString("Size (KB)");
			if(dbname.matches("test")){
				System.out.println("Size of database '" +dbname+ "' is :"+ size +" KB");
			}
		}
		c.close();
	}
	
	public void findTDMatSize(){
		File f = new File("C://Users/Titli Sarkar/workspace/testProject/term-document-matrix-formed.txt");
		long size = f.getTotalSpace()/1024;
		System.out.println("Size of the 'term-document-matrix' is: " + size + " KB");
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		tableSize ts =  new tableSize();
		ts.findDbSpace();
		ts.findTDMatSize();
		
	}
}
