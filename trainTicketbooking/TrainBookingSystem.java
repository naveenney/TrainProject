package trainTicketbooking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrainBookingSystem {

	private static Map<Integer, Train> trains = new HashMap<>();
	private static Map<String, Ticket> tickets = new HashMap<>();
	private static int pnrCounter = 11;

	public static void main(String[] args) {
		TrainData();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Welcome to Indian Railway");
			System.out.println("Enter your option :");
			System.out.println("1=>Add train routes\n" + "2=>List train routes\n" + "3=>Booking\n"
					+ "4=>Get Train status\n" + "5=>Booked tickets\n" + "6=>Cancel Tickets Using PNR\n"
					+ "7=>Cancel Ticket Using Id\n" + "8=>Change ticket status using Id\n"
					+ "9=>Change ticket status of a passenger\n" + "10=>Search Pasenger\n");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				addTrainRoutes(scanner);
			case 2:
				listTrainRoutes();
				break;

			case 3:
				bookTicket(scanner);
				break;
			case 4:
				getTicketStatus(scanner);
				break;
			case 5:
				listBookedTickets();
				break;
			case 6:
				cancelTicket(scanner);
				break;
			case 7:
				cancelTicketPassengerByID(scanner);
				break;
			case 8:
				changeStatusUsingId(scanner);
				break;
			case 9:
				changeTicketStatus(scanner);
				break;
			case 10:
				searchPassengerByID(scanner);
				break;

			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static void TrainData() {

		List<String> routes1 = Arrays.asList("Chennai", "Tambaram", "Chengalpattu", "Tiruchi", "Madurai", "Nellai");
		List<String> routes2 = Arrays.asList("Chennai", "Tiruchy", "Madurai", "Nellai");
		List<String> routes3 = Arrays.asList("Nellur", "ChennaiCentral", "Tambaram", "Chengalpattu", "Tiruchi",
				"Dindugal", "Tirupur", "Coimbatore");

		trains.put(101, new Train(101, "Nellai Express", "1.00", "11.00", routes1, 3, 430));
		trains.put(102, new Train(102, "Bharat Express", "1.00", "7.00", routes2, 3, 530));
		trains.put(103, new Train(103, "Nellur Express", "1.00", "12.00", routes3, 3, 530));
	}

	private static void addTrainRoutes(Scanner scanner) {
		System.out.print("Enter Train number: ");
		int trainNumber = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter Train name: ");
		String trainName = scanner.nextLine();
		System.out.print("Enter Train departure time: ");
		String departureTime = scanner.nextLine();
		System.out.print("Enter Train arrival time: ");
		String arrivalTime = scanner.nextLine();
		System.out.print("Enter Train routes (comma separated): ");
		String routesInput = scanner.nextLine();
		List<String> routes = Arrays.asList(routesInput.split(","));
		System.out.print("Enter Total seats: ");
		int totalSeats = scanner.nextInt();
		System.out.print("Enter Fare: ");
		int fare = scanner.nextInt();
		scanner.nextLine();

		Train newTrain = new Train(trainNumber, trainName, departureTime, arrivalTime, routes, totalSeats, fare);
		trains.put(trainNumber, newTrain);
		System.out.println("Train added successfully.");
	}

	private static void listTrainRoutes() {
		System.out.println("Train Routes:");
		for (Train train : trains.values()) {
			System.out.println(train);
		}
	}

	private static void bookTicket(Scanner scanner) {
		System.out.println("##Plan my journey##");
		System.out.print("From station: ");
		String fromStation = scanner.nextLine();
		System.out.print("To station: ");
		String toStation = scanner.nextLine();

		List<Train> availableTrains = new ArrayList<>();
		for (Train train : trains.values()) {
			if (train.isTrainAvailable(fromStation, toStation)) {
				availableTrains.add(train);
			}
		}

		if (availableTrains.isEmpty()) {
			System.out.println("No trains available for the selected route.");
			return;
		}

		System.out.println("Available trains");
		for (Train train : availableTrains) {
			System.out
					.println("Train no: " + train.trainNumber + " || name: " + train.trainName + " || Departure Time: "
							+ train.departureTime + " || Arrival Time: " + train.arrivalTime + " || Travel Time: "
							+ Math.abs((Integer.parseInt(train.arrivalTime.split("\\.")[0])
									- Integer.parseInt(train.departureTime.split("\\.")[0])))
							+ " || Fare: " + train.fare + " || seats: " + train.totalSeats);
		}

		System.out.print("Enter train number: ");
		int trainNumber = scanner.nextInt();
		scanner.nextLine();

		Train selectedTrain = trains.get(trainNumber);
		if (selectedTrain == null) {
			System.out.println("Invalid train number.");
			return;
		}

		System.out.print("Enter number of passengers: ");
		int numPassengers = scanner.nextInt();
		scanner.nextLine();
		if (numPassengers > selectedTrain.getTotalSeats()) {
			System.out.println(selectedTrain.getTotalSeats()
					+ " only Available in this train(if you ok other tickets for WL (yes or No)");
			String confirmation = scanner.nextLine();
			if (!confirmation.equalsIgnoreCase("Yes"))
				return;

		}
		List<Passenger> passengers = new ArrayList<>();
		for (int i = 1; i <= numPassengers; i++) {
			System.out.println("Enter Passenger " + i + " details:");
			System.out.print("Name: ");
			String name = scanner.nextLine();
			System.out.print("Age: ");
			int age = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Gender: ");
			String gender = scanner.nextLine();
			System.out.print("ID: ");
			String id = scanner.nextLine();
			if (i > selectedTrain.getTotalSeats())
				passengers.add(new Passenger(name, age, gender, id, "WL"));
			else
				passengers.add(new Passenger(name, age, gender, id, "CNF"));

		}

		int totalFare = numPassengers * selectedTrain.fare;

		System.out.println("Total Fare: " + totalFare);

		System.out.println("1=>Pay\n" + "2=>Cancel\n" + "3=>reschedule");
		int paymentOption = scanner.nextInt();
		scanner.nextLine();

		if (paymentOption != 1) {
			System.out.println("Booking cancelled.");
			return;
		}

		String pnr = String.valueOf(pnrCounter++);
		Ticket ticket = new Ticket(pnr, selectedTrain, passengers, totalFare);
		tickets.put(pnr, ticket);

		System.out.println("Tickets booked successfully.");
		selectedTrain.setTotalSeats(selectedTrain.totalSeats - numPassengers);
		System.out.println("Ticket Details");
		System.out.println(ticket);
	}

	private static void getTicketStatus(Scanner scanner) {
		System.out.print("Enter PNR Number: ");
		String pnr = scanner.nextLine();

		Ticket ticket = tickets.get(pnr);
		if (ticket == null) {
			System.out.println("Invalid PNR number.");
			return;
		}

		System.out.println("Ticket Details");
		System.out.println(ticket);
	}

	private static void listBookedTickets() {
		System.out.println("Total Tickets booked: " + tickets.size());
		for (Ticket ticket : tickets.values()) {
			System.out.println("Ticket Details");
			System.out.println(ticket);
		}
	}

	private static void cancelTicket(Scanner scanner) {
		System.out.print("Enter PNR Number: ");
		String pnr = scanner.nextLine();

		Ticket ticket = tickets.remove(pnr);
		if (ticket == null) {
			System.out.println("Invalid PNR number.");
			return;
		}

		System.out.println("Do you want to cancel the ticket? (Yes/No): ");
		String confirmation = scanner.nextLine();
		if (confirmation.equalsIgnoreCase("Yes")) {
			System.out.println("Tickets Cancelled Successfully. Your refund of " + ticket.totalFare
					+ " will be processed soon" + "\tThank you");
		} else {
			System.out.println("Ticket cancellation aborted.");
			tickets.put(pnr, ticket);
		}
	}

	private static void cancelTicketPassengerByID(Scanner scanner) {
		System.out.print("Enter Passenger ID: ");
		String passengerID = scanner.nextLine();
		for (Ticket ticket : tickets.values()) {
			for (Passenger passenger : ticket.passengers) {
				if (passenger.id.equals(passengerID)) {
					System.out.println("Do you want to cancel the ticket? (Yes/No): ");
					String confirmation = scanner.nextLine();
					if (confirmation.equalsIgnoreCase("Yes")) {
						ticket.passengers.remove(passenger);
						System.out.println("Ticket cancelled successfully your refund of " + ticket.getTrain().getFare()
								+ " Will be processed soon\n" + "\tThank you");
						return;
					} else
						return;

				}
			}
		}
		System.out.println("Passenger not found invalid id");

	}

	private static void searchPassengerByID(Scanner scanner) {
		System.out.print("Enter Passenger ID: ");
		String passengerID = scanner.nextLine();

		for (Ticket ticket : tickets.values()) {
			for (Passenger passenger : ticket.passengers) {
				if (passenger.id.equals(passengerID)) {
					System.out.println("Passenger details:");
					System.out.println(passenger);
					System.out.println("Train Number: " + ticket.train.trainNumber);
					return;
				}
			}
		}

		System.out.println("Passenger not found.");
	}

	private static void changeTicketStatus(Scanner scanner) {
		System.out.print("Enter PNR Number: ");
		String pnr = scanner.nextLine();

		Ticket ticket = tickets.get(pnr);
		if (ticket == null) {
			System.out.println("Invalid PNR number.");
			return;
		}

		System.out.print("Enter Ticket status (1.CF 2.WL CANCEL(Except 1 and 2 anynumber): ");
		int statusOption = scanner.nextInt();
		scanner.nextLine();

		String status = statusOption == 1 ? "CF" : statusOption == 2 ? "WL" : "CANCEL";

		
		for (Passenger passenger : ticket.passengers) {
			passenger.status = status;
		}

		System.out.println("Ticket status updated as.." + status);
		System.out.println("Ticket Details");
		System.out.println(ticket);
	}

	public static void changeStatusUsingId(Scanner scanner) {

		System.out.print("Enter PNR Number: ");
		String pnr = scanner.nextLine();

		System.out.print("Enter ID Number: ");
		String id = scanner.nextLine();

		Ticket ticket = tickets.get(pnr);
		if (ticket == null) {
			System.out.println("Invalid PNR number.");
			return;
		}
		int i = 0;
		String status = "";
		System.out.println(ticket.passengers.size());
		while (ticket.passengers.size() > i) {
			if (ticket.passengers.get(i).getId().equals(id)) {
				System.out.print("Enter Ticket status (1.CNF 2.WL  CANCEL(Except 1 and 2 any number): ");
				int statusOption = scanner.nextInt();
				scanner.nextLine();

				status = statusOption == 1 ? "CF" : statusOption == 2 ? "WL" : "CANCEL";
				ticket.passengers.get(i).setStatus(status);

				System.out.println("Ticket status updated as.." + status);
				System.out.println("Ticket Details");
				System.out.println(ticket.passengers.get(i));
				return;
			}
			i++;
		}
	}

}
