/**
 * 
 */
package fr.epita.iam.exceptions;

import java.sql.SQLException;

/**
 * @author tbrou
 *
 */
public class DaoInitializationException extends RuntimeException {
	
	public void initCause(SQLException ex)
	{
		 for (Throwable e : ex) {
		        if (e instanceof SQLException) {
		            if (ignoreSQLException(
		                ((SQLException)e).
		                getSQLState()) == false) {

		            	if ((((SQLException)e).getSQLState()).equalsIgnoreCase("08001"))
		            	System.out.println("Couldnt connect to the database, please ensure your database is running (try running Derby from bin) S.V.P");
		            	
		      
		                System.err.print("SQLState: " +
		                    ((SQLException)e).getSQLState());

		                System.err.print("Error Code: " +
		                    ((SQLException)e).getErrorCode());

		                System.err.println("Message: " + e.getMessage());

		             
		                
		            }
		        }
		    }
		
		
	}

	private static boolean ignoreSQLException(String sqlState) {

	    if (sqlState == null) {
	        System.out.println("The SQL state is not defined!");
	        return false;
	    }

	    // X0Y32: Jar file already exists in schema
	    if (sqlState.equalsIgnoreCase("X0Y32"))
	        return true;

	    // 42Y55: Table already exists in schema
	    if (sqlState.equalsIgnoreCase("42Y55"))
	        return true;

	    return false;
	}
	
}
