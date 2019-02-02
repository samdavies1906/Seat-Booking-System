import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SeatBookingSystem {

	private static Scanner input = new Scanner(System.in);

	private static ArrayList<Seat> seats = new ArrayList<Seat>();
	private static final String PATH = "M:\\data\\seats.txt";

	static String email = "";

	public static void main(String[] args) {

		loadData();

		String choice = "";

		do {
			System.out.println("\n--MAIN MENU --");
			System.out.println("[1] - Display Seats");
			System.out.println("[2] - Book Seat");
			System.out.println("[3] - Cancel Seat");
			System.out.println("[Q] - Quit");
			System.out.print("Pick : ");

			choice = input.next().toUpperCase();

			switch (choice) {

			case "1": {
				displaySeats();
				break;
			}
			case "2": {
				bookSeat();
				break;
			}

			case "3": {
				cancelSeat();
				break;
			}

			}
		} while (!choice.equals("Q"));

		System.out.println("--  Thanks For Using Our system --");

		saveData();
	}

	private static void loadData() {

		Scanner file = null;

		try {

			file = new Scanner(new FileReader(PATH));

			while (file.hasNext()) {
				String[] seatsArray = file.nextLine().split(" ");
				seats.add(new Seat(seatsArray[0], seatsArray[1], Boolean.valueOf(seatsArray[2]), Boolean.valueOf(seatsArray[3]), Boolean.valueOf(seatsArray[4]), Double.valueOf(seatsArray[5]), (seatsArray.length == 7 ? seatsArray[6] : "")));
				//The data from the text file gets split up and placed into an array with is used to fill and an array list 
				//each element of this array is populated with a token. the last token uses a ternary to see if it's been passed 7 tokens
				//if it has it will pass in 7 but if not will just add a blank to the end
			}

			System.out.println("The file was successfully loacted and loaded");
		}

		catch (FileNotFoundException ex) {
			System.err.println("A problem occoured trying to open the file, please try again");
		}

		finally {
			file.close(); //Closes resources to allow data to later to be written to the file
		}

	}

	private static void displaySeats() {

		System.out.println("\n					--List of Seats --");
		System.out
		.println("______________________________________________________________________________________________________________");
		for (Seat s : seats) {
			System.out.printf(s.toString() + "£%.2f | " + s.geteMail() + "\n", Double.valueOf(s.getPrice()));
			//This will display the seats in a user friendly format
			// It will also format the price to 2 decimal places
		}

	}

	private static void bookSeat() {
		ArrayList<String> validation = new ArrayList<String>();
		String email = "";

		System.out.print("\nEnter the seat number you would like to reserve : ");
		String seatNumber = input.next().toUpperCase();

		for (Seat s : seats) {
			validation.add(s.getSeatNumber()); //loads an array with seat numbers
		}

		if ((validation.contains(seatNumber))) { //checks to see if the seat number entered is part of the array
			System.out.print("Enter your email address : "); //if it is user is allowed to continue
			email = input.next();
		}

		else{
			System.out.println("Seat number is invaldi please try again"); //if in's not the user is forced to start over
			bookSeat();
		}

		for (Seat s : seats) {

			if (s.getSeatNumber().equals(seatNumber)) {
				try {
					s.bookSeat(email);
					System.out.println("Seat booked");
				} catch (Exception exc) {
					System.out.println(exc.getMessage());//displays a message to the user if the seat is booked
					similarSeats(); // this is a class which tells you the next best seat by giving the the one with the most similarities 
				}
				
			} 
			
		}

	}

	private static void cancelSeat() {
		ArrayList<String> validation = new ArrayList<String>();
		String email = "";

		System.out.print("Enter the seat number you would like to cancle : ");
		String seatNumber = input.next().toUpperCase();

		for (Seat s : seats) {
			validation.add(s.getSeatNumber()); //loads an array with seat numbers
		}

		if ((validation.contains(seatNumber))) { //checks to see if the seat number entered is part of the array
			System.out.print("Enter your email address : "); //if it is user is allowed to continue
			email = input.next();
		}

		else{
			System.out.println("Seat number is invaldi please try again"); //if in's not the user is forced to start over
			cancelSeat();
		}

		for (Seat s : seats) {
			if (s.getSeatNumber().equals(seatNumber) && s.geteMail().equals(email)) {
				s.cancel();
				System.out.println("Booking cancled");
			}
		}
	}

	private static void saveData() {

		PrintWriter saveToFile = null;

		try {
			saveToFile = new PrintWriter(PATH);
			for (Seat s : seats) {
				saveToFile.println(s.toSave()); //loops through passes the data so it can be turned into the correct format for the file
			}
		}

		catch (IOException exc) {
			System.err.println("Try again");
		} 
		finally {
			saveToFile.close();
		}

	}

	private static void similarSeats() {

		int counter = 0;
		String seatClass;
		boolean window;
		boolean aisle;
		boolean table;
		double price;
		String temp;

		ArrayList<String> similarSeats = new ArrayList<String>();

		System.out.println("Enter below how you would like your seat");

		System.out.print("\nWhat class seat would you like? [STD|1ST] : "); //asks the user for their seat preferences
		seatClass = input.next().toUpperCase();

		System.out.print("Would you like a window seat? [Y|N] : ");
		temp = input.next().toUpperCase();
		if (temp.equals("Y")) {
			window = true; // converts YES or No to TRUE or FALSE respectively
		} else {
			window = false;
		}

		System.out.print("Would you like a aisle seat? [Y|N] : ");
		temp = input.next().toUpperCase();
		if (temp.equals("Y")) {
			aisle = true;
		} else {
			aisle = false;
		}

		System.out.print("Would you like a seat with a table? [Y|N] : ");
		temp = input.next().toUpperCase();
		if (temp.equals("Y")) {
			table = true;
		} else {
			table = false;
		}

		System.out.print("What is your price limit? : ");
		price = Double.valueOf(input.next());

		int i = 6; // i will be the number of similarities
		

		while (i >= 2) { //this will loop through the seats first checking for 5 similarities, then 4, then 3 etc. with a minimum match of 2 needed

			if (similarSeats.isEmpty()) { 

				i--;
				for (Seat s : seats) { 
					counter = 0;
					if (s.getSeatType().equals(seatClass)) {
						counter++;
						if (s.getIsWindow() == window) {
							counter++;
							if (s.getIsAisle() == aisle) {
								counter++;
								if (s.getHasTable() == table) {
									counter++;
									if (s.getPrice() < price) {
										counter++;

									}
								}
							}
						}
					}

					if (counter == i) { //if the counter is equal to the number of similarities then the current seat will be added to the array

						if (s.geteMail() == "") { // it will only be added if it isn't reserved
							similarSeats.add(s.getSeatNumber());
						}
					}

				}

			} else {
				break;
			}

		}

		System.out.println("\nthere is " + similarSeats.size() + " seat(s) unreserved that are similar in " + i + " way(s) to how you specified");
		System.out.print("\nThe seat(s) " + similarSeats);
		displaySeats();
		System.out.print("\n");
	}
}

