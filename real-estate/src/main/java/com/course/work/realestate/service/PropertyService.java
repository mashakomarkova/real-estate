package com.course.work.realestate.service;

import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;
import com.course.work.realestate.entity.User;

import java.util.List;

public interface PropertyService {
    List<Property> findPropertiesByFilters(Integer numberOfRooms, District district, Double priceFrom, Double priceTo);
    List<Property> findAllProperties();
    List<Property> findPropertiesByRealtor(User user);
    Property findPropertyById(Long id);
    void deleteProperty(Long id);
    void saveProperty(Property property);
    void saveReport(Property property, int totalDeals, double totalProfit);
}
