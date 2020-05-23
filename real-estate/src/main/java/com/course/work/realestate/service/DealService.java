package com.course.work.realestate.service;

import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.entity.User;

import java.util.List;

public interface DealService {
    void saveDeal(Deal deal);
    List<Deal> findDealsByClient(User client);
    Deal findDealById(Long id);
}
