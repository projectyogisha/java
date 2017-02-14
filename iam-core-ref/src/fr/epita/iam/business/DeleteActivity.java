/**
 * 
 */
package fr.epita.iam.business;

import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;
import fr.epita.iam.mics.get_input;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author Matthews
 *
 */
public class DeleteActivity {

	public static void execute(Scanner scanner) {
		Identity identity = null;
		String choice = null;
		String email = null;
		IdentityJDBCDAO identityWriter; 
		
		try{
			identityWriter = new IdentityJDBCDAO();
			}catch(DaoInitializationException e)
			{	
				System.out.println("\nFailed to connect to Database.(Could be a database contectivity issue) Exiting...");
			   return;
			}
		
		System.out.print("Welcome to the Deletion tool,");
		do {
		System.out.println("Plz Enter your registered Email to proceed");
	    if ((email = get_input.get_input_email(scanner)).equals("e"))
	    {
			identityWriter.releaseResources();

	    	return;
	    }
	    	try {
			System.out.println("Checking database for records...");
				if (null == (identity = identityWriter.fetch(email))) 
					System.out.println("Email doesnt exist in our database, please try again\n");
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to fetch records from database, try entering your email again\n");
				}
        
		}while ((null == identity) && !email.equals("e"));
	   
		try {
					identityWriter.delete(identity.getUid());
				} 
		catch(Exception e)
		{
			System.out.println("Failed to Delete record. Exiting...");
			return;
		}finally 
		{
					
					identityWriter.releaseResources();

		}
			System.out.println("Deleted the record:\n"+identity.toString());
			
		
		
	}
	
	
	
}
