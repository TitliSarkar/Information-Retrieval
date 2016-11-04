package informationRetrievalAssignment;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class mysqlstore {

	public void establishConnection(String term, int document, int frequency)
			throws ClassNotFoundException, SQLException {
		Connection c;
		PreparedStatement preparedStatement;

		Class.forName("com.mysql.jdbc.Driver");
		c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "titli123");
		String insertTableSQL = "INSERT INTO dbtuple" + "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
				+ "(?,?,?)";
		preparedStatement = (PreparedStatement) c.prepareStatement(insertTableSQL);

		preparedStatement.setString(1, term);
		preparedStatement.setInt(2, document);
		preparedStatement.setInt(3, frequency);

		// execute insert SQL stetement
		preparedStatement.executeUpdate();

		c.close();

	}
	public static void main(String args[]){
		mysqlstore msq = new mysqlstore();
	}

}// end of class
