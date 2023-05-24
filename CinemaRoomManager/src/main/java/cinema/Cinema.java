package cinema;

import java.util.Scanner;

public class Cinema {
    public static Scanner scanner = new Scanner(System.in);
    public static final int DEFAULT_PRICE = 10; // Default ticket's price for small room and back half of the bigger one
    public static final int DEFAULT_DISCOUNT = 2;
    public static final int DEFAULT_SMALL_ROOM_SIZE = 60; // Small room's capacity
    public static final char FREE_SEAT = 'S';
    public static final char BUSY_SEAT = 'B';
    public static char[][] room;
    public static int rows;
    public static int seats;
    public static int purchasedTickets = 0;
    public static int currentIncome = 0;
    public static double percentage = 0.00;
    public static int totalIncome = 0;

    public static void main(String[] args) {

        System.out.println("\nEnter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        if (rows > 0 && seats > 0) {
            room = new char[rows + 1][seats + 1];
            createRoom();
            menu();
        } else {
            System.out.println("Wrong room params");
        }

    }

    static void menu() {
        int variant;

        do {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            variant = scanner.nextInt();

            switch (variant) {
                case 0 -> {
                }
                case 1 -> showSeats();
                case 2 -> buyTicket();
                case 3 -> showStats();
                default -> System.out.println("Wrong input number");
            }
        }
        while (variant != 0);
    }

    private static void createRoom() {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                room[i][j] = FREE_SEAT;
            }
        }
        totalIncome = checkIncome();
    }

    public static void showSeats() {
        // Print top line with numbers
        System.out.print("\nCinema:\n ");

        for (int i = 1; i <= seats; i++) {
            System.out.print(" " + i);
        }
        // Print an array with busy seats
        for (int i = 1; i <= rows; i++) {
            System.out.print("\n" + i);
            for (int j = 1; j <= seats; j++) {
                System.out.print(" " + room[i][j]);
            }
        }
    }

    static void buyTicket() {
        boolean successfulOperation = false;

        do {
            System.out.println("\nEnter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();


            if (row > 0 && seat > 0 && row <= rows && seat <= seats) {
                if (isAvailable(row, seat)) {
                    room[row][seat] = BUSY_SEAT;
                    System.out.println("Ticket price: $" + checkPrice(rows, seats, row));
                    purchasedTickets++;
                    percentage = checkRoomCapacity();
                    successfulOperation = true;
                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            } else {
                System.out.println("Wrong input!");
            }
        } while (!successfulOperation);
    }

    static void showStats() {
        System.out.println("\nNumber of purchased tickets: " + purchasedTickets);
        System.out.printf("\nPercentage: %.2f", percentage);
        System.out.print("%");
        System.out.printf("\nCurrent income: $%d", currentIncome);
        System.out.printf("\nTotal income: $%d", totalIncome);
    }

    static boolean isAvailable(int row, int seat) {
        return room[row][seat] == FREE_SEAT;
    }

    static int checkPrice(int rows, int seats, int row) {
        if (rows * seats <= DEFAULT_SMALL_ROOM_SIZE || row <= rows / 2) {
            currentIncome += DEFAULT_PRICE;
            return DEFAULT_PRICE;
        }
        currentIncome += (DEFAULT_PRICE - DEFAULT_DISCOUNT);
        return DEFAULT_PRICE - DEFAULT_DISCOUNT;
    }

    static int checkIncome() {
        int cost = 0;
        if (rows < 10 && seats < 10) {
            if (rows * seats <= DEFAULT_SMALL_ROOM_SIZE) {
                cost = rows * seats * 10;
            } else {
                int firstHalf = rows / 2;
                int secondHalf = rows - firstHalf;
                cost = (firstHalf * seats * 10) + (secondHalf * seats * 8);
            }
        }
        return cost;
    }

    static double checkRoomCapacity() {
        return (double) (purchasedTickets * 100) / (rows * seats);
    }
}