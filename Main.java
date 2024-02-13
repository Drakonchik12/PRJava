import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Train train = new Train("Express 123", "12345");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add a ticket");
            System.out.println("2. Check for a ticket by last name");
            System.out.println("3. Check for tickets by stations");
            System.out.println("4. Select and add passenger to a ticket");
            System.out.println("5. Exit");
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
                    checkTicketByStations(scanner);
                    break;
                case 4:
                    selectTicketAndAddPassenger(scanner);
                    break;
                case 5:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addTicket(Scanner scanner, Train train) {
        System.out.print("Train Name: ");
        String trainName = scanner.nextLine();

        System.out.print("Train Number: ");
        String trainNumber = scanner.nextLine();

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

        // Create a new Train object
        Train ticketTrain = new Train(trainName, trainNumber);

        // Create a new Ticket object with train information
        Ticket ticket = new Ticket(ticketTrain, departureStation, arrivalStation, departureTime, arrivalTime, price);

        // Add the ticket to the train
        train.addTicket(ticket);

        System.out.println("Do you want to associate a passenger with this ticket? (yes/no): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("yes")) {
            associatePassengerWithTicket(scanner, ticket);
        } else {
            saveTicketWithoutPassengerDetails(ticket);
        }
    }

    private static void associatePassengerWithTicket(Scanner scanner, Ticket ticket) {
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
        ticket.updatePassengerDetails(passenger);

        // Update the file name to include the passenger's last name
        String fileName = "ticket_passenger.csv";
        ticket.writeToFileCSV(fileName);
    }

    private static void saveTicketWithoutPassengerDetails(Ticket ticket) {
        ticket.writeToFileCSV("ticket_no_passenger.csv");
        System.out.println("Ticket information (without passenger details) written to file: ticket_no_passenger.csv");
    }

    private static void checkTicketByLastName(Scanner scanner, Train train) {
        System.out.print("Enter last name to check for a ticket: ");
        String lastName = scanner.nextLine();

        String fileName = "ticket_passenger.csv";
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

    private static void checkTicketByStations(Scanner scanner) {
        System.out.print("Enter departure station: ");
        String departureStation = scanner.nextLine();

        System.out.print("Enter arrival station: ");
        String arrivalStation = scanner.nextLine();

        List<Ticket> matchingTickets = findTicketsByStationsInFile("ticket_no_passenger.csv", departureStation, arrivalStation);

        if (!matchingTickets.isEmpty()) {
            System.out.println("Found matching tickets:");
            for (Ticket ticket : matchingTickets) {
                ticket.displayTicketInfo();
            }
        } else {
            System.out.println("No matching tickets found for the given stations.");
        }
    }

    private static List<Ticket> findTicketsByStationsInFile(String fileName, String departureStation, String arrivalStation) {
        List<Ticket> matchingTickets = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            // Проверка на наличие следующей строки перед попыткой ее считать
            while (fileScanner.hasNextLine()) {
                if (fileScanner.hasNextLine()) {
                    String[] ticketParts = fileScanner.nextLine().split(",");
                    String trainName = (ticketParts.length > 0) ? ticketParts[0].trim() : "";
                    String trainNumber = (ticketParts.length > 1) ? ticketParts[1].trim() : "";
                    String depStationFromFile = (ticketParts.length > 2) ? ticketParts[2].trim() : "";
                    String arrStationFromFile = (ticketParts.length > 3) ? ticketParts[3].trim() : "";
                    String depTimeLineFromFile = (ticketParts.length > 4) ? ticketParts[4].trim() : "";
                    String arrTimeLineFromFile = (ticketParts.length > 5) ? ticketParts[5].trim() : "";
                    double priceFromFile = (ticketParts.length > 6) ? Double.parseDouble(ticketParts[6].trim()) : 0.0;

                    Train ticketTrain = new Train(trainName, trainNumber);
                    Ticket ticket = new Ticket(ticketTrain, depStationFromFile, arrStationFromFile, depTimeLineFromFile, arrTimeLineFromFile, priceFromFile);

                    matchingTickets.add(ticket);
                } else {
                    // Прерывание цикла, если больше нет строк
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return matchingTickets;
    }

    private static void selectTicketAndAddPassenger(Scanner scanner) {
        System.out.print("Enter departure station: ");
        String departureStation = scanner.nextLine();

        System.out.print("Enter arrival station: ");
        String arrivalStation = scanner.nextLine();

        List<Ticket> matchingTickets = findTicketsByStationsInFile("ticket_no_passenger.csv", departureStation, arrivalStation);

        if (!matchingTickets.isEmpty()) {
            System.out.println("Found matching tickets:");
            for (int i = 0; i < matchingTickets.size(); i++) {
                System.out.println((i + 1) + ". ");
                matchingTickets.get(i).displayTicketInfo();
            }

            System.out.print("Do you want to take a ticket? (yes/no): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("yes")) {
                System.out.print("Choose a ticket (enter the corresponding number): ");
                int selectedTicketIndex = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                if (selectedTicketIndex > 0 && selectedTicketIndex <= matchingTickets.size()) {
                    Ticket selectedTicket = matchingTickets.get(selectedTicketIndex - 1);
                    associatePassengerWithTicket(scanner, selectedTicket);
                    removeTicketFromFile("ticket_no_passenger.csv", selectedTicket);
                } else {
                    System.out.println("Invalid selection. Returning to the main menu.");
                }
            } else {
                System.out.println("Returning to the main menu.");
            }
        } else {
            System.out.println("No matching tickets found for the given stations.");
        }
    }

    private static void removeTicketFromFile(String fileName, Ticket ticketToRemove) {
        List<Ticket> remainingTickets = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                if (!currentLine.equals(ticketToStringCSV(ticketToRemove))) {
                    remainingTickets.add(ticketFromStringCSV(currentLine));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Rewrite the file with remaining tickets
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Ticket ticket : remainingTickets) {
                writer.println(ticketToStringCSV(ticket));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String ticketToStringCSV(Ticket ticket) {
        StringBuilder csvLine = new StringBuilder();

        // Добавляем информацию о поезде
        csvLine.append(ticket.getTrain().getTrainName()).append(",");
        csvLine.append(ticket.getTrain().getTrainNumber()).append(",");
        csvLine.append(ticket.getDepartureStation()).append(",");
        csvLine.append(ticket.getArrivalStation()).append(",");
        csvLine.append(ticket.getDepartureTime()).append(",");
        csvLine.append(ticket.getArrivalTime()).append(",");
        csvLine.append(ticket.getPrice());

        // Добавляем информацию о пассажире, если он есть
        Passenger passenger = ticket.getPassenger();
        if (passenger != null) {
            csvLine.append(",");
            csvLine.append(passenger.getFullName()).append(",");
            csvLine.append(passenger.getBirthDate()).append(",");
            csvLine.append(passenger.getPassport());
        }

        return csvLine.toString();
    }

    private static Ticket ticketFromStringCSV(String ticketString) {
        String[] parts = ticketString.split(",");
        String trainName = (parts.length > 0) ? parts[0].trim() : "";
        String trainNumber = (parts.length > 1) ? parts[1].trim() : "";
        String departureStation = (parts.length > 2) ? parts[2].trim() : "";
        String arrivalStation = (parts.length > 3) ? parts[3].trim() : "";
        String departureTime = (parts.length > 4) ? parts[4].trim() : "";
        String arrivalTime = (parts.length > 5) ? parts[5].trim() : "";
        double price = (parts.length > 6) ? Double.parseDouble(parts[6].trim()) : 0.0;

        Train ticketTrain = new Train(trainName, trainNumber);
        Ticket ticket = new Ticket(ticketTrain, departureStation, arrivalStation, departureTime, arrivalTime, price);

        // Если есть информация о пассажире, добавляем ее
        if (parts.length > 7) {
            String firstName = parts[7].trim();
            String lastName = parts[8].trim();
            String patronymic = parts[9].trim();
            String birthDate = parts[10].trim();
            String passport = parts[11].trim();
            Passenger passenger = new Passenger(firstName, lastName, patronymic, birthDate, passport);
            ticket.updatePassengerDetails(passenger);
        }

        return ticket;
    }
}
