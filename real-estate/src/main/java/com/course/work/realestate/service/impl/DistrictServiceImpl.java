package com.course.work.realestate.service.impl;

import com.course.work.realestate.entity.District;
import com.course.work.realestate.repository.DistrictRepository;
import com.course.work.realestate.service.DistrictService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    private DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public List<District> findAllDistricts() {
        return districtRepository.findAll();
    }
}
