import java.util.ArrayList;
import java.util.List;

public class Train {
    private String trainName;
    private String trainNumber;
    private List<Ticket> tickets;

    public Train(String trainName, String trainNumber) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.tickets = new ArrayList<>();
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket findTicketByLastName(String lastName) {
        for (Ticket ticket : tickets) {
            if (ticket.getPassenger() != null && ticket.getPassenger().getLastName().equalsIgnoreCase(lastName)) {
                return ticket;
            }
        }
        return null;
    }

    public List<Ticket> findTicketsByStations(String departureStation, String arrivalStation) {
        List<Ticket> matchingTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getDepartureStation().equalsIgnoreCase(departureStation)
                    && ticket.getArrivalStation().equalsIgnoreCase(arrivalStation)) {
                matchingTickets.add(ticket);
            }
        }
        return matchingTickets;
    }
}
