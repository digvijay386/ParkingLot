package model;

import lombok.Value;
import model.enums.OccupancyStatus;
import com.parkingLot.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Value
public class ParkingFloor {
    String parkingLotId;
    int parkingFloorId;
    int numberOfSlots;
    List<Slot> slots;

    public ParkingFloor(final String parkingLotId,
                        final int parkingFloorId,
                        final int numberOfSlots) {
        this.parkingLotId = parkingLotId;
        this.parkingFloorId = parkingFloorId;
        this.numberOfSlots = numberOfSlots;

        this.slots = constructSlots(parkingLotId, parkingFloorId, numberOfSlots);
    }

    public Slot getSlot(int slotId) {
        return slots.get(slotId);
    }

    public Boolean unParkVehicle(final String ticket) {
        final int slotId = Integer.parseInt(ticket.split("_", 3)[2]);
        final Slot slot = getSlot(slotId);
        return slot.unParkVehicle(ticket);
    }

    public Optional<String> parkVehicle(final Vehicle vehicle) {
        for (final Slot slot: slots) {
            if (slot.isSlotAvailable(vehicle.getVehicleSlotType())) {
                return Optional.ofNullable(slot.parkVehicle(vehicle));
            }
        }

        return Optional.empty();
    }

    private static List<Slot> constructSlots(
            final String parkingLotId,
            final int parkingFloorId,
            final int totalNumberOfSlots) {
        final List<Slot> slots = new ArrayList<>();

        final Slot dummySlot =
                Slot.builder()
                        .slotId(0)
                        .occupancyStatus(OccupancyStatus.OCCUPIED)
                        .floorId(parkingFloorId)
                        .parkingLotId(parkingLotId)
                        .build();

        slots.add(dummySlot);

        for (int slotId = 1; slotId <= totalNumberOfSlots; slotId++) {
            final Slot slot = Slot.builder()
                    .slotId(slotId)
                    .floorId(parkingFloorId)
                    .parkingLotId(parkingLotId)
                    .occupancyStatus(OccupancyStatus.AVAILABLE)
                    .vehicleSlotType(Utils.getVehicleTypeBySlotIdMapping(slotId))
                    .build();
            slots.add(slot);
        }

        return slots;
    }
}
