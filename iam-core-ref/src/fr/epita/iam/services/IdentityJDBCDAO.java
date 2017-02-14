/**
 * 
 */
package fr.epita.iam.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;

/**
 * @author tbrou
 *
 */
public class IdentityJDBCDAO {

	private Connection currentConnection;

	/**
	 * 
	 */
	public IdentityJDBCDAO() throws DaoInitializationException {
			
		try {
			getConnection();
		} catch (SQLException e) {
			DaoInitializationException die = new DaoInitializationException();
			die.initCause(e);
			throw die;
		}
return;
	}

	/**
	 * @throws SQLException
	 * 
	 */
	private Connection getConnection() throws SQLException {
		try {
			this.currentConnection.getSchema();
		} catch (Exception e) {
			// TODO read those information from a file
	
			
			Properties pro = new Properties();
			InputStream in = null;
			
			try {
				in = new FileInputStream("database.properties");
				pro.load(in);
				
			}
			catch (IOException e1)
			{
			System.out.println("Properties file not found or corrupted");	
			}finally {
				if(in != null)
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch bloc
					}
	
			}
			
			String connectionString = "jdbc:derby://"+pro.getProperty("dbURL")+":1527/IAM;create=true";
			this.currentConnection = DriverManager.getConnection(connectionString, pro.getProperty("dbuser"), pro.getProperty("dbpassword"));
		try {
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			
		}
		
		}
		return this.currentConnection;
	}

	
	public void releaseResources() {
		try {
			this.currentConnection.close();
		} catch (Exception e) {
			//TODO trace Exception
		}
	}

	/**
	 * Read all the identities from the database
	 * @return
	 * @throws SQLException
	 */
	public List<Identity> readAllIdentities() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();

		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID");
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String email = rs.getString("IDENTITY_EMAIL");
			Identity identity = new Identity(String.valueOf(uid), displayName, email);
			identities.add(identity);
		}
statement.close();
		return identities;
	}

	/**
	 * write an identity in the database
	 * @param identity
	 * @throws SQLException
	 */
	public void write(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "INSERT INTO IDENTITIES(IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) VALUES(?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getDisplayName());
		pstmt.setString(2, identity.getEmail());
		// TODO implement date for identity
		pstmt.setString(3, null);

		pstmt.execute();
		pstmt.close();
	}

	
	public Identity fetch(String email) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from IDENTITIES where IDENTITY_EMAIL=?");
		statement.setString(1, email);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID");
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String email1 = rs.getString("IDENTITY_EMAIL");
			return new Identity(String.valueOf(uid), displayName, email1);
		
		}
		statement.close();
		return null;
		
	}

	public void update(Identity identity) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("update IDENTITIES set IDENTITY_EMAIL=?, IDENTITY_DISPLAYNAME=? WHERE IDENTITY_ID=? ");
		statement.setString(1,identity.getEmail());
		statement.setString(2,identity.getDisplayName());
		statement.setString(3,identity.getUid());
		
		
	statement.execute();
		statement.close();	
	}
	
	public void delete(String uid) throws SQLException {
		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement(" DELETE FROM IDENTITIES  WHERE IDENTITY_ID=? ");
		statement.setString(1,uid);

	statement.execute();
			statement.close();
	}
	
	public boolean authenticate(String user, String password) throws SQLException {

		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from ADMIN where ADMIN_USER=? AND ADMIN_PASS=?");
		statement.setString(1,user);
		statement.setString(2,password);

		ResultSet rs = statement.executeQuery();
		
	return rs.next();
	}



}




