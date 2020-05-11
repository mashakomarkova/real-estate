package com.course.work.realestate.service.impl;

import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;
import com.course.work.realestate.repository.PropertyRepository;
import com.course.work.realestate.service.PropertyService;
import com.course.work.realestate.specification.PropertySpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<Property> findPropertiesByFilters(Integer numberOfRooms, District district, Double priceFrom, Double priceTo) {
        Specification<Property> specification = new Specification<Property>() {
            public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                if (numberOfRooms != null) {
                    predicates.add(builder.equal(root.get("numberOfRooms"), numberOfRooms));
                }
                if (district != null) {
                    predicates.add(builder.equal(root.get("district"), district));
                }
                if (priceTo != null && priceFrom != null) {
                    predicates.add(builder.between(root.get("price"), priceFrom, priceTo));
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return propertyRepository.findAll(specification);
    }
}
