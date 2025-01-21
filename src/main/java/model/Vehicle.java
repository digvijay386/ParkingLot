package model;

import lombok.Builder;
import lombok.Value;
import model.enums.VehicleSlotType;

@Builder
@Value
public class Vehicle {
    String registrationNumber;
    String color;
    VehicleSlotType vehicleSlotType;

    public static Vehicle from(final String vehicleSlotType,
                               final String registrationNumber,
                               final String color) {
        final VehicleSlotType vehicleSlotTypeEnum =
                VehicleSlotType.valueOf(vehicleSlotType);

        return Vehicle.builder()
                .color(color)
                .vehicleSlotType(vehicleSlotTypeEnum)
                .registrationNumber(registrationNumber)
                .build();
    }
}
