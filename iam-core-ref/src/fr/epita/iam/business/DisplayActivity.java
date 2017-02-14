/**
 * 
 */
package fr.epita.iam.business;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author Matthews
 *
 */
public class DisplayActivity {

	public static void execute()
	{
		
		IdentityJDBCDAO identityWriter = null;
		List<Identity> records = null;
		
		try{
		identityWriter = new IdentityJDBCDAO();
		}catch(DaoInitializationException e)
		{	
			System.out.println("\nFailed to connect to Database.(Could be a database contectivity issue) Exiting...");
		   return;
		}
		
		try {
		records = identityWriter.readAllIdentities();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to retrive records from database Exiting.....");
			return;
		}finally {
			identityWriter.releaseResources();
			
		}
		
		
		for (Identity temp : records) {
			System.out.println(temp.toString());
		}
		
	}
	
	
}
