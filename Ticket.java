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

    public void writeToFileCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            StringBuilder csvLine = new StringBuilder();

            // Добавляем информацию о поезде
            csvLine.append(train.getTrainName()).append(",");
            csvLine.append(train.getTrainNumber()).append(",");
            csvLine.append(departureStation).append(",");
            csvLine.append(arrivalStation).append(",");
            csvLine.append(departureTime).append(",");
            csvLine.append(arrivalTime).append(",");
            csvLine.append(price);

            // Добавляем информацию о пассажире, если он есть
            if (passenger != null) {
                csvLine.append(",");
                csvLine.append(passenger.getFullName()).append(",");
                csvLine.append(passenger.getBirthDate()).append(",");
                csvLine.append(passenger.getPassport());
            }

            // Записываем строку в файл и добавляем новую строку
            writer.write(csvLine.toString() + "\n");

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
    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public Train getTrain() {
        return train;
    }

    public double getPrice() {
         return price;
        
    }
}
