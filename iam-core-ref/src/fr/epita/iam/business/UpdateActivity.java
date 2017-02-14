/**
 * 
 */
package fr.epita.iam.business;

import java.sql.SQLException;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;
import fr.epita.iam.mics.get_input;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author Matthews
 *
 */
public class UpdateActivity {
	
	
	@SuppressWarnings("finally")
	public static void execute(Scanner scanner) {
		Identity identity = null;
		String choice = null;
		String email = null;	
		IdentityJDBCDAO identityWriter = null;
		
		
		
		System.out.print("Welcome to the Updation tool,");
		try {
			identityWriter = new IdentityJDBCDAO();	
			
		do {
			System.out.println("Plz Enter your registered Email to proceed");
			
			if ((email = get_input.get_input_email(scanner)).equals("e"))
			{
				
				identityWriter.releaseResources();
				return;

			}
				
				if (null == (identity = identityWriter.fetch(email))) 
					System.out.println("Email doesnt exist in our database, pelase try again");

			
		}while ((null == identity) && !email.equals("e"));
		}
		catch (DaoInitializationException e){
	    	   System.out.println("Cannot update without database connection. Exiting...");
	    	   return;
	       }
		
		catch (SQLException e) {
				// TODO Auto-generated catch block
		
				System.out.println("Failed to retrive records from Database.(Could be a database contectivity issue),Exiting...");
					return;
			}
		catch (Exception e){
    	   System.out.println("UNkown Error, when querying DATABASE, Exiting...");
    	  return;
       }finally{
			identityWriter.releaseResources();				

		}


		
		
		
			do
			{
		System.out.println("Current Record: "+identity.toString()+"\nSelect a field to update\na) Email \nb) Name");
        choice = scanner.nextLine();
		
		switch (choice)
		{
		case "a":
			identity.setEmail(get_input.get_input_email(scanner)); 
			if ((identity.getEmail()).equals("e"))
			{
				identityWriter.releaseResources();
				return;
	
			}
			break;
		case "b":
			System.out.println("<type \"e\" to exit>\nEnter new Name");
			identity.setDisplayName(scanner.nextLine()); 
			if ((identity.getDisplayName()).equals("e"))
			{
				identityWriter.releaseResources();
				return;
	
			}
			break;

		 default:
			 System.out.println("enter a correct option to update");
		}
		}while (!choice.equals("b")&& !choice.equals("a"));
		    try {
				identityWriter.update(identity);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to update records from Database.(Could be a database contectivity issue) Exiting...");

				return;				
			}finally{
				identityWriter.releaseResources();				

			}
		System.out.println("UPdated record as:"+identity.toString());
		
		identityWriter.releaseResources();
	
	}
	
		
}
