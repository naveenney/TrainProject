package trainTicketbooking;


import java.util.*;

public class Train {
    int trainNumber;
    String trainName;
    String departureTime;
    String arrivalTime;
    List<String> routes;
    int totalSeats;
    int fare;

    public Train(int trainNumber, String trainName, String departureTime, String arrivalTime, List<String> routes, int totalSeats, int fare) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.routes = routes;
        this.totalSeats = totalSeats;
        this.fare = fare;
    }

    public boolean isTrainAvailable(String fromStation, String toStation) {
        return routes.contains(fromStation) && routes.contains(toStation) && routes.indexOf(fromStation) < routes.indexOf(toStation);
    }

    @Override
    public String toString() {
        return "Train number : " + trainNumber + "\nTrain name : " + trainName + "\nTrain departure time : " + departureTime +
                "\nTrain arrival time : " + arrivalTime + "\nTrain routes : " + String.join(",", routes) +
                "\nTotal seats : " + totalSeats + "\nFare : " + fare + "\n";
    }
}

