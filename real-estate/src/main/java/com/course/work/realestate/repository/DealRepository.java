package com.course.work.realestate.repository;

import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findAllByClient(User client);
}
