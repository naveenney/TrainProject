package trainTicketbooking;

import java.util.*;

public class Train {
	int trainNumber;
	String trainName;
	String departureTime;
	String arrivalTime;
	List<String> routes;
	int totalSeats;

	public int getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public List<String> getRoutes() {
		return routes;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	int fare;

	public Train(int trainNumber, String trainName, String departureTime, String arrivalTime, List<String> routes,
			int totalSeats, int fare) {
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.routes = routes;
		this.totalSeats = totalSeats;
		this.fare = fare;
	}

	public boolean isTrainAvailable(String fromStation, String toStation) {
		return routes.contains(fromStation) && routes.contains(toStation)
				&& routes.indexOf(fromStation) < routes.indexOf(toStation);
	}

	@Override
	public String toString() {
		return "Train number : " + trainNumber + "\nTrain name : " + trainName + "\nTrain departure time : "
				+ departureTime + "\nTrain arrival time : " + arrivalTime + "\nTrain routes : "
				+ String.join(",", routes) + "\nTotal seats : " + totalSeats + "\nFare : " + fare + "\n";
	}
}
