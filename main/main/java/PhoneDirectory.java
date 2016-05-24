package main.java;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
/**
 * Phone Directory class
 * @author angelc
 *
 */
public class PhoneDirectory {

		/**Reads properties file, phone.properties
		 * 
		 * @return file location from phone.properties file
		 * @throws IOException
		 */
		public String propValue() throws IOException{
			InputStream inputstream;
			Properties prop = new Properties();
			inputstream = getClass().getClassLoader().getResourceAsStream("resources/phone.properties");
			prop.load(inputstream);
			
			//returns location value from property file
			String filename = prop.getProperty("location");
			return filename;
		}

		//Phone Directory File read from Property file
		String filename = propValue();
		File file = new File(filename);
		
		// Phone Directory Functions
		enum Function {
			ADD, DELETE, CHANGE, SEARCH, SWITCH, EXIT;
		};
		
		/**
		 * Ask for User input to conduct functions of Phone Directory
		 * 
		 * @throws IOException
		 */
		public PhoneDirectory() throws IOException {
			int value = 1;
			Function ToDo = Function.ADD;
			Scanner input = new Scanner(System.in);
			String name = null, number = null;
			
			// while user does not want to exit
			while (value != 5) {
				System.out.println("Enter corresponding number if you would like to add(1), change(2), delete(3) or "
						+ "search(4) for contact in directory or exit(5)");
				if(input.hasNextInt()){
					value = input.nextInt();
				} else {
					//if incorrect user input
					System.out.println("Sorry incorrect input");
					value = 0;
					input.nextLine();
				}
				//conducts users wanted function
				if(value == 1){ToDo = Function.ADD;}
				
				else if(value == 2){ToDo = Function.CHANGE;}
				
				else if(value == 3){ToDo = Function.DELETE;}
				
				else if(value == 4){ToDo = Function.SEARCH;}
				
				else if(value == 5){ToDo = Function.EXIT;}
				
				else if(value == 0){ToDo = Function.EXIT;}
				
			switch(ToDo) {
					case ADD:
						//user input name and number
						System.out.println("Enter name and number to add to Directory: ");
						name = input.nextLine();
						number = input.nextLine();
						//add to Directory file
						addEntry(name, number);
						break;
						
					case DELETE:
						//user input name
						System.out.println("Enter name to remove from Directory: ");
						name = input.next();
						//delete contact from Directory file
						deleteEntry(name);
						break;
						
					case CHANGE:
						//user input name and new number
						System.out.println("Enter name to change you would like to change and new number: ");
						name = input.next();
						//System.out.println("Enter new number for " + name + ":");
						number = input.next();
						//call function to change entry
						changeEntry(name, number);
						break;
						
					case SEARCH:
						//user input name
						System.out.println("Enter name to get number: ");
						//get Number from file
						getNumber(input.next());
						break;
						
					case EXIT:
						//leave Phone Directory 
						break;
						
					default:
						break;
				}
			}
			input.close();
		}
		
		/**
		 * Adds contact to Phone Directory file
		 * @param name
		 * @param number
		 * @throws IOException
		 */
		void addEntry(String name, String number) throws IOException  {
			//open file writer and append user input to File
			FileWriter fw = new FileWriter(file, true);
			fw.append(name + " " + number + "\n");
			fw.close();
		}
		
		/**
		 * deletes contact from Phone Directory file
		 * @param name
		 * @throws IOException
		 */
		void deleteEntry(String name) throws IOException{
			//Create Temporary file
			File tempfile = new File("TempDirectory.txt");
			// monitors if contact name is found in file
			boolean found = false;
			Scanner scan = new Scanner(file);
			FileWriter fw = new FileWriter(tempfile, true);
			
			//scan each line of Directory file to find name to remove
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				
				//adds good lines to temporary file
				if(!line.contains(name)){
					fw.append(line + "\n");
				}
				
				// if contact name is found
				else{
					found = true;
				}
			}
			
			// if contact is found in file
			if(found){
				tempfile.renameTo(file);
				System.out.println("Contact removed from Directory");
			}
			
			//if contact does not exist
			else{
				System.out.println("Contact not found in Directory");
				tempfile.delete();
			}
			
			scan.close();
			fw.close();
		}
		
		/**
		 * Find number of contact entered in Phone Directory
		 * @param name
		 * @return
		 * @throws IOException
		 */
		String getNumber(String name) throws IOException{
			String current_number = " : Contact not found";
			
			//Scans Phone directory file
			Scanner scan = new Scanner(file);
			scan.useDelimiter(" ");
			
			//Iterates through Phone Directory
			while(scan.hasNext()){
				String line = scan.next();
				
				// If element in file is the contact name, save next element: number 
				if (line.contains(name)){
					current_number = scan.nextLine();
					break;
				}	
			}
			
			scan.close();
			//Prints contact 
			System.out.println(name + "'s number is " + current_number);
			return current_number;
		}
		
		/**
		 * Changes number for given contact name
		 * @param name
		 * @param number
		 * @throws IOException
		 */
		void changeEntry(String name, String number) throws IOException{
			//deletes old contact and adds updated contact
			deleteEntry(name);
			addEntry(name, number);
			System.out.println("Updated contact added!");
		}
}
