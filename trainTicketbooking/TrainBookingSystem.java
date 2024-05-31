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
    private static int pnrCounter = 10;

    public static void main(String[] args) {
    	
        loadTrainData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Indian Railway");
            System.out.println("Enter your option :");
            System.out.println("1=>Booking\n"+"2=>Get PNR status\n"+"3=>Booked tickets\n"+"4=>Cancel Tickets\n"+"5=>Search passenger\n"+"6=>Change ticket status of a passenger\n"+"7=>List train routes\n"+"8=>Add train routes");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bookTicket(scanner);
                    break;
                case 2:
                    getPNRStatus(scanner);
                    break;
                case 3:
                    listBookedTickets();
                    break;
                case 4:
                    cancelTicket(scanner);
                    break;
                case 5:
                    searchPassengerByID(scanner);
                    break;
                case 6:
                    changeTicketStatus(scanner);
                    break;
                case 7:
                    listTrainRoutes();
                    break;
                case 8:
                    addTrainRoutes(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void loadTrainData() {
    	
       List<String> routes1 = Arrays.asList("ChennaiCentral", "Tambaram", "Chengalpattu", "Tiruchy", "Madurai", "Nellai");
        List<String> routes2 = Arrays.asList("ChennaiCentral", "Tambaram", "Chengalpattu", "Tiruchy", "Madurai", "Nellai", "Tuticorin");
        List<String> routes3 = Arrays.asList("ChennaiCentral", "Tambaram", "Chengalpattu", "Tiruchy", "Dindugal", "Tirupur", "Coimbatore");

        trains.put(101, new Train(101, "Nellai Express", "1.00", "11.00", routes1, 10, 430));
        trains.put(102, new Train(102, "Muthunagar Express", "23.00", "5.00", routes2, 10, 530));
        trains.put(103, new Train(103, "Kovai Express", "23.00", "5.00", routes3, 10, 530));
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
            System.out.println("Train no: " + train.trainNumber + " || name: " + train.trainName + " || Departure Time: " + train.departureTime + " || Arrival Time: " + train.arrivalTime + " || Travel Time: " + (Integer.parseInt(train.arrivalTime.split("\\.")[0]) - Integer.parseInt(train.departureTime.split("\\.")[0])) + " || Fare: " + train.fare + " || seats: " + train.totalSeats);
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
            passengers.add(new Passenger(name, age, gender, id, "CNF"));
        }

        int totalFare = numPassengers * selectedTrain.fare;
        System.out.println("Total Fare: " + totalFare);
        System.out.println("1-Pay\n"+"2-Cancel\n"+"3-reschedule");
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
        System.out.println("Ticket Details");
        System.out.println(ticket);
    }

    private static void getPNRStatus(Scanner scanner) {
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
            System.out.println("Ticket Cancelled Successfully. Your refund of " + ticket.totalFare + " will be processed soon.");
        } else {
            System.out.println("Ticket cancellation aborted.");
            tickets.put(pnr, ticket); 
        }
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

        System.out.print("Enter Ticket status (1.CNF 2.CANCEL): ");
        int statusOption = scanner.nextInt();
        scanner.nextLine(); 

        String status = statusOption == 1 ? "CNF" : "CANCEL";
        for (Passenger passenger : ticket.passengers) {
            passenger.status = status;
        }

        System.out.println("Ticket status updated as \"" + status + "\"");
        System.out.println("Ticket Details");
        System.out.println(ticket);
    }

    private static void listTrainRoutes() {
        System.out.println("Train Routes:");
        for (Train train : trains.values()) {
            System.out.println(train);
        }
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
}
