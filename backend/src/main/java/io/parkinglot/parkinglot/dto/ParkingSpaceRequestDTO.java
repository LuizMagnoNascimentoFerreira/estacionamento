package io.parkinglot.parkinglot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceRequestDTO {
    private Integer userId;
    private String parkingSpaceLocation;
    private Integer occupationTimeInMinutes;
}
