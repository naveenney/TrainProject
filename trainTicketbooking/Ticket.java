package trainTicketbooking;

import java.util.*;

public class Ticket {
	String pnr;
	Train train;
	List<Passenger> passengers;
	int totalFare;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public int getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(int totalFare) {
		this.totalFare = totalFare;
	}

	public Ticket(String pnr, Train train, List<Passenger> passengers, int totalFare) {
		this.pnr = pnr;
		this.train = train;
		this.passengers = passengers;
		this.totalFare = totalFare;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Train Details\n-------------\n");
		sb.append("Train Number : ").append(train.trainNumber).append(" || Train Name : ").append(train.trainName)
				.append(" || Departure Time : ").append(train.departureTime).append(" || Arrival Time : ")
				.append(train.arrivalTime).append(" || From : ").append(train.routes.get(0)).append(" || To : ")
				.append(train.routes.get(train.routes.size() - 1)).append(" || PNR No : ").append(pnr)
				.append(" || Total Fare : ").append(totalFare).append("\n");
		sb.append("Passenger Details\n-----------------\n");
		for (Passenger p : passengers) {
			sb.append(p.toString()).append("\n");
		}
		return sb.toString();
	}
}
