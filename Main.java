
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
        String fileName = "ticket_" + lastName + ".txt";
        ticket.writeToFile(fileName);
    }

    private static void saveTicketWithoutPassengerDetails(Ticket ticket) {
        ticket.writeToFile("ticket_no_passenger.txt");
        System.out.println("Ticket information (without passenger details) written to file: ticket_no_passenger.txt");
    }

    private static void checkTicketByLastName(Scanner scanner, Train train) {
        System.out.print("Enter last name to check for a ticket: ");
        String lastName = scanner.nextLine();

        String fileName = "ticket_" + lastName + ".txt";
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

        List<Ticket> matchingTickets = findTicketsByStationsInFile("ticket_no_passenger.txt", departureStation, arrivalStation);

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
                String[] trainNameParts = fileScanner.nextLine().split(":");
                String trainName = (trainNameParts.length > 1) ? trainNameParts[1].trim() : "";
    
                String[] trainNumberParts = fileScanner.nextLine().split(":");
                String trainNumber = (trainNumberParts.length > 1) ? trainNumberParts[1].trim() : "";
    
                String[] depStationParts = fileScanner.nextLine().split(":");
                String depStationFromFile = (depStationParts.length > 1) ? depStationParts[1].trim() : "";
    
                String[] arrStationParts = fileScanner.nextLine().split(":");
                String arrStationFromFile = (arrStationParts.length > 1) ? arrStationParts[1].trim() : "";

                String[]  depTimeLine = fileScanner.nextLine().split(":");
                String  depTimeLineFromFile = (depTimeLine.length > 1) ? depTimeLine[1].trim() : "";

                String[]  arrTimeLine = fileScanner.nextLine().split(":");
                String  arrTimeLineFromFile = (arrTimeLine.length > 1) ? arrTimeLine[1].trim() : "";

                String priceLine = fileScanner.nextLine();
                double priceFromFile = Double.parseDouble(priceLine.substring(priceLine.indexOf(":") + 1).trim());

                Train ticketTrain = new Train(trainName, trainNumber);
                Ticket ticket= new Ticket(ticketTrain, depStationFromFile, arrStationFromFile, depTimeLineFromFile, arrTimeLineFromFile, priceFromFile);

    
                    matchingTickets.add(ticket);
        } 
        else {
            // Прерывание цикла, если больше нет строк
            break;
        }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    
        return matchingTickets;
    }

    // private static String extractTime(String line) {
    //     // Assuming the time format is HH:mm
    //     String regex = ".*: (\\d{2}):(\\d{2})";
    //     java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
    //     java.util.regex.Matcher matcher = pattern.matcher(line);
    
    //     if (matcher.find()) {
    //         String hours = matcher.group(1);
    //         String minutes = matcher.group(2);
    //         return hours + ":" + minutes;
    //     } else {
    //         return "";
    //     }
    // }
   
        private static void selectTicketAndAddPassenger(Scanner scanner) {
            System.out.print("Enter departure station: ");
            String departureStation = scanner.nextLine();

            System.out.print("Enter arrival station: ");
            String arrivalStation = scanner.nextLine();

            List<Ticket> matchingTickets = findTicketsByStationsInFile("ticket_no_passenger.txt", departureStation, arrivalStation);

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
                        removeTicketFromFile("ticket_no_passenger.txt", selectedTicket);
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
                if (!currentLine.equals(ticketToRemove.toString())) {
                    remainingTickets.add(ticketFromString(currentLine));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Rewrite the file with remaining tickets
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Ticket ticket : remainingTickets) {
                writer.println(ticket.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Ticket ticketFromString(String ticketString) {
        String[] parts = ticketString.split(":");
        String trainName = (parts.length > 1) ? parts[1].trim() : "";
        String trainNumber = (parts.length > 2) ? parts[2].trim() : "";
        String departureStation = (parts.length > 3) ? parts[3].trim() : "";
        String arrivalStation = (parts.length > 4) ? parts[4].trim() : "";
        String departureTime = (parts.length > 5) ? parts[5].trim() : "";
        String arrivalTime = (parts.length > 6) ? parts[6].trim() : "";
        double price = (parts.length > 7) ? Double.parseDouble(parts[7].trim()) : 0.0;

        Train ticketTrain = new Train(trainName, trainNumber);
        return new Ticket(ticketTrain, departureStation, arrivalStation, departureTime, arrivalTime, price);
    }
}
