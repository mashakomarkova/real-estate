package com.course.work.realestate.controller;

import com.course.work.realestate.entity.District;
import com.course.work.realestate.service.DistrictService;
import com.course.work.realestate.service.PropertyService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PropertyController {

    private PropertyService propertyService;
    private DistrictService districtService;

    public PropertyController(PropertyService propertyService, DistrictService districtService) {
        this.propertyService = propertyService;
        this.districtService = districtService;
    }

    @GetMapping("/searchProperty")
    public ModelAndView propertySearcherPage() {
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        return modelAndView;
    }

    @PostMapping("/searchProperty")
    public ModelAndView searchProperty(@RequestParam String district, @RequestParam String numberOfRooms,
                                       @RequestParam String priceFrom, @RequestParam String priceTo) {
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        modelAndView.addObject("properties", propertyService.findPropertiesByFilters(
                !numberOfRooms.isEmpty() ? Integer.parseInt(numberOfRooms) : null,
                new Gson().fromJson(district, District.class),
                !priceFrom.isEmpty() ? Double.parseDouble(priceFrom) : null,
                !priceTo.isEmpty() ? Double.parseDouble(priceTo) : null
        ));

        return modelAndView;
    }

}
