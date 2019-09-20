package com.petia.cardemo.repository;


import com.petia.cardemo.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make_cars);


}
