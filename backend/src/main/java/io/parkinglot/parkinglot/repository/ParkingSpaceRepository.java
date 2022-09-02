package io.parkinglot.parkinglot.repository;

import io.parkinglot.parkinglot.model.entity.ParkingSpace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParkingSpaceRepository extends CrudRepository<ParkingSpace,Integer> {
    public Optional<List<ParkingSpace>> findByStatus(String status);
    public Optional<ParkingSpace> findByUserId(Integer userId);
    public Optional<ParkingSpace> findByLocation (String location);
    public Optional<ParkingSpace> findByUserCpd(String cpd);
}
