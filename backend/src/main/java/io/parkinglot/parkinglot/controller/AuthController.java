package io.parkinglot.parkinglot.controller;

import io.parkinglot.parkinglot.dto.UserDTO;
import io.parkinglot.parkinglot.dto.UserRegisterDTO;
import io.parkinglot.parkinglot.model.entity.User;
import io.parkinglot.parkinglot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @PostMapping ("/login")
    public ResponseEntity doLogin(@RequestBody UserRegisterDTO userRegisterDTO){
        Optional<User> userOptional = userRepository.findByCpd(userRegisterDTO.getCpd());
        User user;
        if(userOptional.isPresent()){
            user = userOptional.get();
            if(user.getPassword().equals(userRegisterDTO.getPassword())){
                return new ResponseEntity(new UserDTO(user), HttpStatus.OK);
            }else{
                return new ResponseEntity("Dados inválidos",HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity("Dados inválidos",HttpStatus.UNAUTHORIZED);
    }
}
