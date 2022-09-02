package io.parkinglot.parkinglot.dto;

import io.parkinglot.parkinglot.model.ParkingSpaceStatusEnum;
import io.parkinglot.parkinglot.model.entity.ParkingSpace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceQueryDTO {
    private String location;
    private String status;
    public ParkingSpaceQueryDTO(ParkingSpace parkingSpace){
        this.location = parkingSpace.getLocation();
        this.status = parkingSpace.getStatus().toString();
    }
}
