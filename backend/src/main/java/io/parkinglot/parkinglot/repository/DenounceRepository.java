package io.parkinglot.parkinglot.repository;

import io.parkinglot.parkinglot.model.entity.Denounce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenounceRepository extends CrudRepository<Denounce,Integer> {
}
