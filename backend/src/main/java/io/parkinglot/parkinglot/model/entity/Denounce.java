package io.parkinglot.parkinglot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Denounce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn
    private User whistleblower;
    @ManyToOne
    @JoinColumn
    private User denounced;
    private String complaint;
    private LocalDate denounceDate;
    @Column(name = "image_path")
    private String imagePath;
}
