/**
 * 
 */
package fr.epita.iam.launcher;

import java.sql.SQLException;
import java.util.Scanner;

import fr.epita.iam.business.CreateActivity;
import fr.epita.iam.business.DeleteActivity;
import fr.epita.iam.business.DisplayActivity;
import fr.epita.iam.business.UpdateActivity;
import fr.epita.iam.exceptions.DaoInitializationException;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author tbrou
 *
 */
public class ConsoleLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the IAM software");
		Scanner scanner = new Scanner(System.in);
		
		//authentication
		if (!authenticate(scanner)){
			end(scanner);
			return;
		}
		String choice ;
				do{
		//menu
		
		System.out.println("Please select an action :");
		System.out.println("a. create an Identity");
		System.out.println("b. modify an Identity");
		System.out.println("c. delete an Identity");
		System.out.println("d. Display all identities");
		System.out.println("e. quit");
	    choice = scanner.nextLine();
		switch (choice) {
		case "a":
			//Create
			CreateActivity.execute(scanner);
			break;
		case "b":
			//Modify
			UpdateActivity.execute(scanner);		
			break;
			
		case "c":
			//Delete
			DeleteActivity.execute(scanner);
			break;
		case "d":
			DisplayActivity.execute();
			break;

		case "e":
			//Quit
			break;
		
		default:
			System.out.println("Your choice is not recognized");
			break;
		}
		}while(!("e").equals(choice));
		
		end(scanner);
	
	}

	/**
	 * @param scanner
	 */
	private static void end(Scanner scanner) {
		System.out.println("Thanks for using this application, good bye!");
		scanner.close();
	}

	/**
	 * @param scanner
	 */
	private static boolean authenticate(Scanner scanner) {
		
		IdentityJDBCDAO identityWriter = null;
		
		try{
		identityWriter = new IdentityJDBCDAO();
		}catch(DaoInitializationException e)
		{	

			System.out.println("Something went wrong and we could authenticate you try restart the application");
		   return false;
		}
		
		System.out.println("Please type your login : ");
		String login = scanner.nextLine();
		
		System.out.println("Please type your password : ");
		String password = scanner.nextLine();
		
		try {
			if (identityWriter.authenticate(login, password)){
				System.out.println("Athentication was successful");
				identityWriter.releaseResources();
				
				return true;
			}else{
				System.out.println("Athentication failed");
				identityWriter.releaseResources();
				
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println("Something went wrong and we could authenticate you try restart the application");
			return false;
			}finally{
				identityWriter.releaseResources();
				}
		
	}

}
