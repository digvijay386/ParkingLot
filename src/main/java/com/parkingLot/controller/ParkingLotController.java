package com.parkingLot.controller;

import model.ParkingLot;
import model.Vehicle;
import com.parkingLot.utils.Utils;

import java.util.Objects;

public class ParkingLotController {

    private static ParkingLotController instance;
    private static ParkingLot parkingLot;

    private ParkingLotController() {}

    public static synchronized ParkingLotController getInstance() {
        if (Objects.isNull(instance)) {
            instance =  new ParkingLotController();
        }
        return instance;
    }

    public void createParkingLot(
            final String parkingLotId,
            final int numberOfFloors,
            final int numberOfSlotsPerFloor) {

        parkingLot = ParkingLot.getInstance(
                parkingLotId,
                numberOfFloors,
                numberOfSlotsPerFloor);
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public String parkVehicle(final String vehicleType,
                              final String registrationNumber,
                              final String color) {
        final Vehicle vehicle =
                Vehicle.from(
                        vehicleType,
                        registrationNumber,
                        color);

        final ParkingLot parkingLot = getParkingLot();
        return parkingLot.parkVehicle(vehicle);
    }

    public Boolean unParkVehicle(final String ticket) {
        Utils.validateTicket(ticket);

        final ParkingLot parkingLot = getParkingLot();

        return parkingLot.unParkVehicle(ticket);
    }
}
