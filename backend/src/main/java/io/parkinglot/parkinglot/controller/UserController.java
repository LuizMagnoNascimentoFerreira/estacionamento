package io.parkinglot.parkinglot.controller;

import io.parkinglot.parkinglot.dto.DenounceDTO;
import io.parkinglot.parkinglot.model.entity.Denounce;
import io.parkinglot.parkinglot.model.entity.ParkingSpace;
import io.parkinglot.parkinglot.repository.DenounceRepository;
import io.parkinglot.parkinglot.repository.ParkingSpaceRepository;
import io.parkinglot.parkinglot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    private DenounceRepository denounceRepository;
    @Autowired
    UserRepository userRepository;
    //Realiza uma denúncia
    @PostMapping("/denounce")
    public ResponseEntity denounce(DenounceDTO denounceDTO){
        Denounce denounce = denounceDTO.convert(userRepository);
        denounceRepository.save(denounce);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    //Retorna todos os usuários cadastrados
    @GetMapping
    public ResponseEntity getAllUsers(){
        return new ResponseEntity(userRepository.findAll(),HttpStatus.OK);
    }
}
