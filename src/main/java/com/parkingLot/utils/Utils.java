package com.parkingLot.utils;

import com.parkingLot.controller.ParkingLotController;
import model.ParkingLot;
import model.enums.VehicleSlotType;
import model.exceptions.InvalidTicketException;

public class Utils {
    private Utils() {}

    public static void validateTicket(final String ticket) {
        try {
            final String[] parts = ticket.split("_", 3);
            final int floorId = Integer.parseInt(parts[1]);
            final int slotId = Integer.parseInt(parts[2]);

            final ParkingLot parkingLot =
                    ParkingLotController.getInstance().getParkingLot();

            final int numberOfFloors = parkingLot.getNumberOfParkingFloors();
            final int numberOfSlotsPerFloor = parkingLot.getNumberOfSlotsPerFloor();

            if (floorId < 1
                    || floorId > numberOfFloors
                    || slotId < 1
                    || slotId > numberOfSlotsPerFloor) {
                throw new InvalidTicketException("Invalid Ticket");
            }
        } catch (final Exception e) {
            throw new InvalidTicketException("Invalid Ticket");
        }
    }

    public static VehicleSlotType getVehicleTypeBySlotIdMapping(int slotId) {
        if (slotId == 1) {
            return VehicleSlotType.TRUCK;
        } else if (slotId == 2 || slotId == 3) {
            return VehicleSlotType.BIKE;
        }
        return VehicleSlotType.CAR;
    }
}
