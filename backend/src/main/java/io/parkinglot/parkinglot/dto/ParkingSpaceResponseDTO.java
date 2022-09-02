package io.parkinglot.parkinglot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.parkinglot.parkinglot.model.entity.ParkingSpace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceResponseDTO {
    @JsonProperty("location")
    private String parkingSpaceLocation;
    private String expirationDate;
    public ParkingSpaceResponseDTO(ParkingSpace parkingSpace){
        this.parkingSpaceLocation = parkingSpace.getLocation();
        this.expirationDate = parkingSpace.getExpirationDate().toString();
    }
}
