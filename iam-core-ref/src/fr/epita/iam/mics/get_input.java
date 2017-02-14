/**
 * 
 */
package fr.epita.iam.mics;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthews
 *
 */
public class get_input {


	 static final String EMAILREGEX =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	 
	private static boolean check_empty(String string)
	{
	return (0 == string.length());
	}
	
	private static boolean check_email(String string)
	{
		if (check_empty(string))
			return false;
				
		Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAILREGEX);	
  	    
    	matcher = pattern.matcher(string);
		return matcher.matches();

		
		
	}
	
	public static String get_input_email (Scanner scanner)
	{
		String string = null;
	    boolean flag = false;
		System.out.println("<type \"e\" to exit>\n>>Please enter an email address");	

		do {
			if (flag)
			System.out.println("<type \"e\" to exit>\n>>You have entered an incorrect email or empty string,Please enter a valid email address");	
			string =  scanner.nextLine();
			flag = true;        
		}while(!check_email(string) && !(string.equals("e")));
		
		return string;
	}
}