package com.course.work.realestate.repository;

import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByArea(double area);
    List<Property> findAllByPriceBetween(double priceFrom, double priceTo);
    List<Property> findAllByDistrict(District district);
    List<Property> findAllByNumberOfRooms(int numberOfRooms);

}
