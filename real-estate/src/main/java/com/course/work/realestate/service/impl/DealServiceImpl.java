package com.course.work.realestate.service.impl;

import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.entity.User;
import com.course.work.realestate.repository.DealRepository;
import com.course.work.realestate.service.DealService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Deal> findDealsByClient(User client) {
        return dealRepository.findAllByClient(client);
    }

    @Override
    public Deal findDealById(Long id) {
        return dealRepository.findById(id).get();
    }
}
