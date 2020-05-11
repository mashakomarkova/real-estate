package com.course.work.realestate.service.impl;

import com.course.work.realestate.entity.Role;
import com.course.work.realestate.repository.RoleRepository;
import com.course.work.realestate.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
