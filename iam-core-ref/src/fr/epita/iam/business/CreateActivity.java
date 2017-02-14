/**
 * 
 */
package fr.epita.iam.business;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;
import fr.epita.iam.mics.get_input;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author tbrou
 *
 */
public class CreateActivity {
	
	
	public static void execute(Scanner scanner){
		System.out.println("Identity Creation");
		
		//persist the identity somewhere
		IdentityJDBCDAO identityWriter = null;
				
		try{
		identityWriter = new IdentityJDBCDAO();
		}catch(DaoInitializationException e)
		{	http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2568658
			System.out.println("\nFailed to create in Database.(Could be a database contectivity issue) Exiting...");
		   return;
		}
		String email = get_input.get_input_email(scanner);
		if (("e").equals(email))
		{
			identityWriter.releaseResources();
			return;

		}
		System.out.println("<type \"e\" to exit>\n>>Please enter a displayName");
		String displayName = scanner.nextLine();
			if (("e").equals(displayName))
		{
			identityWriter.releaseResources();
			return;

		}
		
		
		Identity identity = new Identity("",displayName, email);
		try{			
		identityWriter.write(identity);
		}catch (SQLIntegrityConstraintViolationException e)
		{

			System.out.println("Email ID already exists in the data");
	 	try {
				identity = identityWriter.fetch(email);
				
				System.out.println("Existing Record: "+identity.toString());			
			} catch (Exception e1) {}
			
		 return;		
		}
		catch (Exception e)
		{
			System.out.println("UNkown Error, when querying DATABASE, Exiting...");
	    	  return;
		}finally
		{
			identityWriter.releaseResources();
			
		}
		System.out.println("Creation Done");
		
	}
	
}
	