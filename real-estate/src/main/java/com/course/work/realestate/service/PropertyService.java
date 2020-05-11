package com.course.work.realestate.service;

import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;

import java.util.List;

public interface PropertyService {
    List<Property> findPropertiesByFilters(Integer numberOfRooms, District district, Double priceFrom, Double priceTo);
}
