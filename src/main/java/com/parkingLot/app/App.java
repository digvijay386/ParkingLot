package com.parkingLot.app;

import com.parkingLot.controller.ParkingLotController;
import model.exceptions.InvalidTicketException;
import model.exceptions.SlotUnavailableException;

import java.util.Scanner;

public class App {

    public App() {}

    public static void main(String[] args) {
        final ParkingLotController parkingLotController = ParkingLotController.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("System has been initialised.");
        System.out.println("Please enter commands");

        while (true) {
            final String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting system.");
                break;
            }

            String[] parts = command.split(" ");
            String operation = parts[0];

            switch (operation) {
                case "createParkingLot":
                    if (parts.length != 4) {
                        System.out.println("Usage: createParkingLot <parkingLotId> <numberOfFloors> <numberOfSlotsPerFloor>");
                        break;
                    }
                    final String parkingLotId = parts[1];
                    final int floors = Integer.parseInt(parts[2]);
                    final int slotsPerFloor = Integer.parseInt(parts[3]);
                    try {
                        parkingLotController.createParkingLot(parkingLotId, floors, slotsPerFloor);
                        System.out.println("Created a parking lot with parkingLotId: " + parkingLotId +
                                " floors: " + floors +
                                " slotsPerFloor: " + slotsPerFloor);
                    } catch (final Exception ex) {
                        System.out.println("An unknown error occurred; please try again later.");
                    }
                    break;

                case "parkVehicle":
                    if (parts.length != 5) {
                        System.out.println("Usage: parkVehicle <parkingLotId> <vehicleType> <registrationNumber> <color>");
                        break;
                    }

                    final String vehicleType = parts[2];
                    final String registrationNumber = parts[3];
                    final String color = parts[4];

                    try {
                        final String ticket =
                                parkingLotController.parkVehicle(vehicleType, registrationNumber, color);
                        System.out.println("Got a Ticket: " + ticket);
                    } catch (final IllegalArgumentException ex) {
                        System.out.println("Please check the value of arguments provided and retry.");
                    } catch (final SlotUnavailableException ex) {
                        System.out.println("An unknown error occurred; please try again later.");
                    }

                    break;
                case "unParkVehicle":
                    if (parts.length != 2) {
                        System.out.println("Usage: unParkVehicle <ticketId>");
                        break;
                    }
                    String ticketId = parts[1];
                    try {
                        boolean success = parkingLotController.unParkVehicle(ticketId);
                        if (success) {
                            System.out.println("Vehicle successfully unParked with ticket ID: " + ticketId);
                        } else {
                            System.out.println("An unknown error occurred; please try again later.");
                        }
                    } catch (final InvalidTicketException ex) {
                        System.out.println("Invalid ticket ID or the vehicle has already been unParked.");
                    }

                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }

        scanner.close();
    }
}
