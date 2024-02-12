import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Passenger {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthDate;
    private String passport;

    public Passenger(String firstName, String lastName, String patronymic, String birthDate, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.passport = passport;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassport() {
        return passport;
    }
    
    public String getBirthDate() {
        return birthDate;
    }

}

class Ticket {
    private String departureStation;
    private String arrivalStation;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private Passenger passenger;

    public Ticket(String departureStation, String arrivalStation, String departureTime, String arrivalTime, double price, Passenger passenger) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.passenger = passenger;
    }

    public void displayTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Departure Station: " + departureStation);
        System.out.println("Arrival Station: " + arrivalStation);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
        System.out.println("Price: " + price);
        System.out.println("Passenger Information:");
        System.out.println("Full Name: " + passenger.getFullName());
        System.out.println("Birth Date: " + passenger.getBirthDate());
        System.out.println("Passport: " + passenger.getPassport());
    }

    public void writeToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Ticket Information:\n");
            writer.write("Departure Station: " + departureStation + "\n");
            writer.write("Arrival Station: " + arrivalStation + "\n");
            writer.write("Departure Time: " + departureTime + "\n");
            writer.write("Arrival Time: " + arrivalTime + "\n");
            writer.write("Price: " + price + "\n");
            writer.write("Passenger Information:\n");
            writer.write("Full Name: " + passenger.getFullName() + "\n");
            writer.write("Birth Date: " + passenger.getBirthDate() + "\n");
            writer.write("Passport: " + passenger.getPassport() + "\n");

            System.out.println("Ticket information written to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getLastName() {
        return passenger.getLastName();
    }
}

class Train {
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
            if (ticket.getLastName().equalsIgnoreCase(lastName)) {
                return ticket;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Train train = new Train("Express 123", "12345");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add a ticket");
            System.out.println("2. Check for a ticket by last name");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    addTicket(scanner, train);
                    break;
                case 2:
                    checkTicketByLastName(scanner, train);
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addTicket(Scanner scanner, Train train) {
        System.out.println("Enter passenger details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Patronymic: ");
        String patronymic = scanner.nextLine();

        System.out.print("Birth Date: ");
        String birthDate = scanner.nextLine();

        System.out.print("Passport: ");
        String passport = scanner.nextLine();

        Passenger passenger = new Passenger(firstName, lastName, patronymic, birthDate, passport);

        System.out.println("Enter ticket details:");
        System.out.print("Departure Station: ");
        String departureStation = scanner.nextLine();

        System.out.print("Arrival Station: ");
        String arrivalStation = scanner.nextLine();

        System.out.print("Departure Time: ");
        String departureTime = scanner.nextLine();

        System.out.print("Arrival Time: ");
        String arrivalTime = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character

        Ticket ticket = new Ticket(departureStation, arrivalStation, departureTime, arrivalTime, price, passenger);
        train.addTicket(ticket);

        String fileName = "ticket" + ticket.getLastName() + ".txt";
        ticket.writeToFile(fileName);
    }

private static void checkTicketByLastName(Scanner scanner, Train train) {
    System.out.print("Enter last name to check for a ticket: ");
    String lastName = scanner.nextLine();

    String fileName = "ticket" + lastName + ".txt";
    File file = new File(fileName);

    if (file.exists()) {
        try {
            Scanner fileScanner = new Scanner(file);

            // Reading and displaying ticket information
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("No ticket found for " + lastName);
    }
}
}