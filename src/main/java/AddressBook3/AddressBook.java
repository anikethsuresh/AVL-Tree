package AddressBook3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AddressBook {

	public static void main(String[] args) throws FileNotFoundException {
		Table table = new Table();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Do you want to open a file?	(Followed by .txt extension)");
		String fileYorN = scan.nextLine();
		// If the user wants to open a file
		if(fileYorN.equalsIgnoreCase("y")) {
			System.out.println("Enter the name of the file to open:");
			String openFile = scan.nextLine();
			File openThisFile = new File(openFile);
			// Check if the file exists
			if(openThisFile.exists()){
				Scanner readFromFile = new Scanner(openThisFile);
				// Read from file
				while (readFromFile.hasNextLine()) {
					String name = readFromFile.nextLine();
					String address = readFromFile.nextLine();
					table.insert(name, address);
					if(readFromFile.hasNextLine()) {
						readFromFile.nextLine();
					}     
				}
				readFromFile.close();
			}
			
			int numOfElements = table.display();
			System.out.println(numOfElements + " entries are present in the Address Book");
		}
		String choice;
		String key,value;
		while(true) {
			System.out.println("\nAdd a name (n)\r\n" + 
					"Look up a name (l)\r\n" + 
					"Update address (u)\r\n" + 
					"Delete an entry (d)\r\n" + 
					"Display all entries (a)\r\n" + 
					"Save (s)\r\n" +
					"Quit (q)\n" +
					"Enter your choice");
			choice = scan.nextLine();
			switch(choice) {
			case "n":
				System.out.println("Add a name\n");
				System.out.println("Enter a name:");
				key = scan.nextLine();
				System.out.println("Enter an address");
				value = scan.nextLine();
				boolean successful = table.insert(key, value);
				if(successful) {
					System.out.println("Successfully Inserted");
				}
				else {
					System.out.println(key + " already exists");
				}
				break;
				
			case "l":
				System.out.println("Enter a name to lookup\n");
				key = scan.nextLine();
				String result;
				result = table.lookUp(key);
				if(result==null) {
					System.out.println(key + " not found");
				}
				else {
					System.out.println(result);
				}
				break;
				
			case "u":
				System.out.println("Enter the name to update\n");
				key = scan.nextLine();
				System.out.println("Enter a New Value for " + key);
				value = scan.nextLine();
				boolean tryToInsert = table.update(key,value);
				if(tryToInsert) {
					System.out.println("Updated Successfully");
				}
				else {
					System.out.println(key + " is absent");
				}
				break;
			
			case "d":
				System.out.println("Enter the name to delete\n");
				key = scan.nextLine();
				successful = table.delete(key);
				if(successful) {
					System.out.println("Successfully Deleted");
				}
				else {
					System.out.println(key + " does not exist");
				}
				break;
				
			case "a":
				int numOfElements = table.display();
				System.out.println(numOfElements + " entries are present in the Address Book");
				break;
			
			case "s":
				System.out.println("Enter the name of a file to save to: (Followed by .txt extension)");
				String saveTo = scan.nextLine();
				try {
					table.save(saveTo);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "q":
				System.exit(0);
				
			default:
				System.out.println("Invalid Entry\n");
			}	 
		}
	}

}