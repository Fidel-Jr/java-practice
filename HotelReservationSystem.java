
import java.util.Scanner;

public class MyClass {
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);

        int hotel[][] = new int[7][5];

        int choice;

        while (true) {
            System.out.println("\n=== HOTEL RESERVATION SYSTEM ===");
            System.out.println("1. View Rooms");
            System.out.println("2. Check In");
            System.out.println("3. Check Out");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nRoom Status:");
                    for (int i = 6; i >= 0; i--) {
                        System.out.print("Floor " + (i + 1) + ": ");
                        for (int j = 0; j < 5; j++) {
                            System.out.print("[" + hotel[i][j] + "]");
                        }
                        System.out.println();
                    }
                    break;

                case 2:
                    System.out.print("Enter floor (1-7): ");
                    int inFloor = sc.nextInt();
                    System.out.print("Enter room (1-5): ");
                    int inRoom = sc.nextInt();

                    int fIndexIn = inFloor - 1;
                    int rIndexIn = inRoom - 1;

                    if (fIndexIn < 0 || fIndexIn >= 7 || rIndexIn < 0 || rIndexIn >= 5) {
                        System.out.println("Invalid floor or room number!");
                    } else if (hotel[fIndexIn][rIndexIn] == 0) {
                        hotel[fIndexIn][rIndexIn] = 1;
                        System.out.println("Check-in successful!");
                    } else {
                        System.out.println("Room already occupied!");
                    }
                    break;

                case 3:
                    System.out.print("Enter floor (1-7): ");
                    int outFloor = sc.nextInt();
                    System.out.print("Enter room (1-5): ");
                    int outRoom = sc.nextInt();

                    int fIndexOut = outFloor - 1;
                    int rIndexOut = outRoom - 1;

                    if (fIndexOut < 0 || fIndexOut >= 7 || rIndexOut < 0 || rIndexOut >= 5) {
                        System.out.println("Invalid floor or room number!");
                    } else if (hotel[fIndexOut][rIndexOut] == 1) {
                        hotel[fIndexOut][rIndexOut] = 0;
                        System.out.println("Check-out successful!");
                    } else {
                        System.out.println("Room already empty!");
                    }
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
  }
}
