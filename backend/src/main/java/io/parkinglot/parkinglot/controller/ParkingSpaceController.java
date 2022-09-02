package io.parkinglot.parkinglot.controller;

import io.parkinglot.parkinglot.dto.ParkingSpaceQueryDTO;
import io.parkinglot.parkinglot.dto.ParkingSpaceRequestDTO;
import io.parkinglot.parkinglot.dto.ParkingSpaceResponseDTO;
import io.parkinglot.parkinglot.model.ParkingSpaceStatusEnum;
import io.parkinglot.parkinglot.model.entity.ParkingSpace;
import io.parkinglot.parkinglot.model.entity.User;
import io.parkinglot.parkinglot.repository.ParkingSpaceRepository;
import io.parkinglot.parkinglot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/parking-spaces")
public class ParkingSpaceController {
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    private UserRepository userRepository;
    //Obtém todas as vagas de estacionamento e seus respectivos status
    @GetMapping
    public ResponseEntity<Iterable<ParkingSpaceQueryDTO>> getAllParkingSpaces(){
        List<ParkingSpaceQueryDTO> parkingSpaceQueryDTOList = new ArrayList<ParkingSpaceQueryDTO>();
        parkingSpaceRepository.findAll().forEach(parkingSpace -> {
            parkingSpaceQueryDTOList.add(new ParkingSpaceQueryDTO(parkingSpace));
        });
        return new ResponseEntity(parkingSpaceQueryDTOList,HttpStatus.OK);
    }
    //Obtém somente as vagas de estacionamento disponíveis
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpace>> getAvailableParkingSpaces(){
        Optional<List<ParkingSpace>> availableParkingSpaces = parkingSpaceRepository.findByStatus("FREE");
        if(availableParkingSpaces.isPresent()){
            return new ResponseEntity<>(availableParkingSpaces.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Ocupa uma vaga de estacionamento
    @PostMapping("/occupy")
    public ResponseEntity<ParkingSpaceResponseDTO> occupyParkingSpace(@RequestBody ParkingSpaceRequestDTO parkingSpaceRequestDTO){
        ParkingSpace parkingSpace = parkingSpaceRepository.findByLocation(parkingSpaceRequestDTO.getParkingSpaceLocation()).get();
        //A vaga já está ocupada.
        if(parkingSpace.getStatus() == ParkingSpaceStatusEnum.OCCUPIED){
            return new ResponseEntity("A vaga já está ocupada.",HttpStatus.UNAUTHORIZED);
        }    User user = userRepository.findById(parkingSpaceRequestDTO.getUserId()).get();
        parkingSpace.setUser(user);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt,BR"));
        gregorianCalendar.add(Calendar.MINUTE,parkingSpaceRequestDTO.getOccupationTimeInMinutes());
        parkingSpace.setExpirationDate(gregorianCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        parkingSpace.setStatus(ParkingSpaceStatusEnum.OCCUPIED);
        parkingSpaceRepository.save(parkingSpace);
        return new ResponseEntity<>(new ParkingSpaceResponseDTO(parkingSpace),HttpStatus.OK);
    }
    //Retorna a vaga de estacionamento ocupada pelo usuário
    @GetMapping("/occupied-by")
    public ResponseEntity<ParkingSpaceResponseDTO> getOccupiedParkingSpace(@RequestParam("userId") Integer userId){
        Optional<ParkingSpace> parkingSpaceOptional = parkingSpaceRepository.findByUserId(userId);
        if(parkingSpaceOptional.isPresent()){
            ParkingSpace parkingSpace = parkingSpaceOptional.get();
            return new ResponseEntity<>(new ParkingSpaceResponseDTO(parkingSpace),HttpStatus.OK);
        }
        return new ResponseEntity(null,HttpStatus.OK);
    }
    //Libera uma vaga de estacionamento
    @PostMapping("/free")
    public ResponseEntity freeParkingSpace(@RequestBody ParkingSpaceRequestDTO parkingSpaceRequestDTO){

        ParkingSpace parkingSpace = parkingSpaceRepository.findByLocation(parkingSpaceRequestDTO.getParkingSpaceLocation()).get();
        if(parkingSpace.getStatus() == ParkingSpaceStatusEnum.FREE){
            return new ResponseEntity<>("A vaga em questão já está liberada",HttpStatus.UNAUTHORIZED);
        }
        parkingSpace.setUser(null);
        parkingSpace.setExpirationDate(null);
        parkingSpace.setStatus(ParkingSpaceStatusEnum.FREE);
        parkingSpaceRepository.save(parkingSpace);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Atualiza o status da vaga com base na data de expiração
    @PostMapping("/update")
    public ResponseEntity updateParkingSpacesStatus(){
        Iterable<ParkingSpace> parkingSpaces = parkingSpaceRepository.findAll();
        parkingSpaces.forEach(parkingSpace -> {
            LocalDateTime currentTimestamp = LocalDateTime.now();
            //Significa que o tempo expirou, portanto, a vaga é liberada no sistema
            if(parkingSpace.getExpirationDate() != null && currentTimestamp.isAfter(parkingSpace.getExpirationDate())){
                parkingSpace.setUser(null);
                parkingSpace.setExpirationDate(null);
                parkingSpace.setStatus(ParkingSpaceStatusEnum.FREE);
                parkingSpaceRepository.save(parkingSpace);
            }
        });
        return new ResponseEntity(HttpStatus.OK);
      }
    }
