package model;

import lombok.Builder;
import lombok.Getter;
import model.enums.OccupancyStatus;
import model.enums.VehicleSlotType;
import model.exceptions.InvalidTicketException;

@Getter
@Builder
public class Slot {
    private final String parkingLotId;
    private final int floorId;
    private final int slotId;
    private final VehicleSlotType vehicleSlotType;
    private OccupancyStatus occupancyStatus;
    private Vehicle vehicle;
    private String ticket;

    public boolean isSlotAvailable(final VehicleSlotType vehicleSlotType) {
        return OccupancyStatus.AVAILABLE.equals(occupancyStatus)
                && vehicleSlotType.equals(this.vehicleSlotType);
    }

    public String parkVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.occupancyStatus = OccupancyStatus.OCCUPIED;
        this.ticket = parkingLotId + "_" + floorId + "_" + slotId;
        return this.ticket;
    }

    public Boolean unParkVehicle(final String ticket) {
        if (OccupancyStatus.AVAILABLE.equals(getOccupancyStatus())) {
            throw new InvalidTicketException("This ticket is invalid; the slot is not occupied");
        }

        if (this.ticket.equals(ticket)) {
            this.occupancyStatus = OccupancyStatus.AVAILABLE;
            return true;
        }

        return false;
    }
}