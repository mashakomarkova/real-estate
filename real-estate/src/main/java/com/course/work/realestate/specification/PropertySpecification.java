package com.course.work.realestate.specification;

import com.course.work.realestate.entity.Property;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PropertySpecification implements Specification<Property> {

    private String district;
    private String numberOfRooms;
    private String priceFrom;
    private String priceTo;

    public PropertySpecification(String district, String numberOfRooms, String priceFrom, String priceTo) {
        this.district = district;
        this.numberOfRooms = numberOfRooms;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    @Override
    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}

