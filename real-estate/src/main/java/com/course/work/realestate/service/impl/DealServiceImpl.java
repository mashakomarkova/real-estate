package com.course.work.realestate.service.impl;

import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.repository.DealRepository;
import com.course.work.realestate.service.DealService;
import org.springframework.stereotype.Service;

@Service
public class DealServiceImpl implements DealService {

    private DealRepository dealRepository;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public void saveDeal(Deal deal) {
        dealRepository.save(deal);
    }
}
