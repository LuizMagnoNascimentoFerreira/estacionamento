package io.parkinglot.parkinglot.dto;

import io.parkinglot.parkinglot.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String cpd;
    private String name;
    public UserDTO(User user){
        this.id = user.getId();
        this.cpd = user.getCpd();
        this.name = user.getName();
    }
}
