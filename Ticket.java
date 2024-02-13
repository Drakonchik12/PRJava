import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private Train train;
    private String departureStation;
    private String arrivalStation;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private Passenger passenger;
    

    public Ticket(Train train, String departureStation, String arrivalStation, String departureTime, String arrivalTime, double price) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.train = train;
    }

    

    public void displayTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Train Name: " + train.getTrainName());
        System.out.println("Train Number: " + train.getTrainNumber());
        System.out.println("Departure Station: " + departureStation);
        System.out.println("Arrival Station: " + arrivalStation);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
        System.out.println("Price: " + price);

        if (passenger != null) {
            System.out.println("Passenger Information:");
            System.out.println("Full Name: " + passenger.getFullName());
            System.out.println("Birth Date: " + passenger.getBirthDate());
            System.out.println("Passport: " + passenger.getPassport());
        }

     
    }

    public void writeToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("Ticket Information:\n");
            writer.write("Train Name: " + train.getTrainName() + "\n");
            writer.write("Train Number: " + train.getTrainNumber() + "\n");
            writer.write("Departure Station: " + departureStation + "\n");
            writer.write("Arrival Station: " + arrivalStation + "\n");
            writer.write("Departure Time: " + departureTime + "\n");
            writer.write("Arrival Time: " + arrivalTime + "\n");
            writer.write("Price: " + price + "\n");

            if (passenger != null) {
                writer.write("Passenger Information:\n");
                writer.write("Full Name: " + passenger.getFullName() + "\n");
                writer.write("Birth Date: " + passenger.getBirthDate() + "\n");
                writer.write("Passport: " + passenger.getPassport() + "\n");
            }

            System.out.println("Ticket information written to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePassengerDetails(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public Train getTrain() {
        return train;
    }
}
