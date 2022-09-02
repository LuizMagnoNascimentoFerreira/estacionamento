package io.parkinglot.parkinglot.dto;

import io.parkinglot.parkinglot.model.entity.Denounce;
import io.parkinglot.parkinglot.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DenounceDTO {
    private Integer whistleblowerId;
    private Integer denouncedId;
    private String complaint;
    private Integer parkingSpaceId;

    public Denounce convert(UserRepository userRepository) {
        Denounce denounce = new Denounce();
        denounce.setWhistleblower(userRepository.findById(this.whistleblowerId).get());
        denounce.setDenounced(userRepository.findById(this.getDenouncedId()).get());
        denounce.setDenounceDate(LocalDate.now());
        denounce.setComplaint(this.complaint);
        return denounce;
    }
}
