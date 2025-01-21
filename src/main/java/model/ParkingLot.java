package model;

import lombok.Value;
import model.exceptions.SlotUnavailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Value
public class ParkingLot {
    String parkingLotId;
    int numberOfParkingFloors;
    int numberOfSlotsPerFloor;
    List<ParkingFloor> parkingFloors;

    static ParkingLot instance;

    public static synchronized ParkingLot getInstance(final String parkingLotId,
                                                      final int numberOfParkingFloors,
                                                      final int numberOfSlotsPerFloor) {
        if (Objects.isNull(instance)) {
            instance = new ParkingLot(parkingLotId,
                            numberOfParkingFloors,
                            numberOfSlotsPerFloor);
        }
        return instance;
    }

    private ParkingLot(final String parkingLotId,
                       final int numberOfParkingFloors,
                       final int numberOfSlotsPerFloor) {
        this.parkingLotId = parkingLotId;
        this.numberOfParkingFloors = numberOfParkingFloors;
        this.numberOfSlotsPerFloor = numberOfSlotsPerFloor;

        this.parkingFloors = constructParkingFloors(
                this.parkingLotId,
                this.numberOfParkingFloors,
                this.numberOfSlotsPerFloor);
    }

    public String parkVehicle(final Vehicle vehicle) {
        for (final ParkingFloor parkingFloor: parkingFloors) {
            final Optional<String> ticket = parkingFloor.parkVehicle(vehicle);
            if (ticket.isPresent()) {
                return ticket.get();
            }
        }

        throw new SlotUnavailableException(String.format(
                "No slot available for a vehicle of type: %s",
                vehicle.getVehicleSlotType().name()));
    }

    public Boolean unParkVehicle(final String ticket) {
        final int floorId = Integer.parseInt(ticket.split("_", 3)[1]);
        final ParkingFloor parkingFloor = getParkingFloor(floorId);
        return parkingFloor.unParkVehicle(ticket);
    }

    public ParkingFloor getParkingFloor(final int floorId) {
        return this.parkingFloors.get(floorId);
    }

    private List<ParkingFloor> constructParkingFloors(final String parkingLotId,
                                                      final int numberOfParkingFloors,
                                                      final int numberOfSlotsPerFloor) {
        final List<ParkingFloor> parkingFloors = new ArrayList<>();

        final ParkingFloor dummyParkingFloor =
                new ParkingFloor(parkingLotId, 0, 0);

        parkingFloors.add(dummyParkingFloor);

        for (int parkingFloorId = 1; parkingFloorId <= numberOfParkingFloors; parkingFloorId++) {
            final ParkingFloor parkingFloor =
                    new ParkingFloor(parkingLotId, parkingFloorId, numberOfSlotsPerFloor);
            parkingFloors.add(parkingFloor);
        }

        return parkingFloors;
    }
}
