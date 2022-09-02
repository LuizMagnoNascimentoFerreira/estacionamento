package io.parkinglot.parkinglot.model.entity;

import io.parkinglot.parkinglot.model.ParkingSpaceStatusEnum;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String location;
    @OneToOne
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'FREE'")
    private ParkingSpaceStatusEnum status;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}
