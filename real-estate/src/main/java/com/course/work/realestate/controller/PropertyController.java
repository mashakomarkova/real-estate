package com.course.work.realestate.controller;

import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;
import com.course.work.realestate.entity.User;
import com.course.work.realestate.service.DealService;
import com.course.work.realestate.service.DistrictService;
import com.course.work.realestate.service.PropertyService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PropertyController {

    private PropertyService propertyService;
    private DistrictService districtService;
    private DealService dealService;
    @Autowired
    private HttpServletRequest request;

    public PropertyController(PropertyService propertyService, DistrictService districtService, DealService dealService) {
        this.propertyService = propertyService;
        this.districtService = districtService;
        this.dealService = dealService;
    }

    @GetMapping("/filterProperty")
    public ModelAndView propertySearcherPage() {
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        return modelAndView;
    }

    @PostMapping("/filterProperty")
    public ModelAndView filterProperty(@RequestParam String district, @RequestParam String numberOfRooms,
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

    @PostMapping("/bookProperty")
    public ModelAndView bookProperty(@RequestParam String arrivalDate, @RequestParam String departureDate,
                                     @RequestParam String propertyId) {
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        Deal deal = getDeal(arrivalDate, departureDate, propertyId);
        dealService.saveDeal(deal);
        return modelAndView;
    }

    private Deal getDeal(String arrivalDate, String departureDate, String propertyId) {
        Deal deal = new Deal();
        Date arrivalDateSql = Date.valueOf(arrivalDate);
        deal.setArrivalDate(arrivalDateSql);
        Date departureDateSql = Date.valueOf(departureDate);
        deal.setDepartureDate(departureDateSql);
        deal.setDateOfDeal(Date.valueOf(LocalDate.now()));
        User client = (User) request.getSession().getAttribute("user");
        deal.setClient(client);
        deal.setStatus("accepted");
        Property property = propertyService.findPropertyById(Long.parseLong(propertyId));
        property.getDeals().add(deal);
        deal.setProperty(property);
        double totalPrice = property.getPrice() * ((departureDateSql.getTime() - arrivalDateSql.getTime()) / (3600000 * 24));
        deal.setTotalPrice(totalPrice);
        return deal;
    }
    @GetMapping("/propertyById/{id}")
    public ModelAndView findPropertyById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("property");
        modelAndView.addObject("property", propertyService.findPropertyById(id));
        return modelAndView;
    }

    @PostMapping("/searchProperty")
    public ModelAndView searchPropertyByDate(@RequestParam String date) {
        Date dateOfOccupation = Date.valueOf(date);
        List<Property> properties = propertyService.findAllProperties();
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        modelAndView.addObject("properties", properties);
        Map<Long, Boolean> busyEstateMap = getBusyEstateMap(properties, dateOfOccupation);
        modelAndView.addObject("busyEstateMap", busyEstateMap);
        return modelAndView;
    }

    private Map<Long, Boolean> getBusyEstateMap(List<Property> properties, Date dateOfOccupation) {
        Map<Long, Boolean> busyEstateMap = new HashMap<>();
        for (Property property : properties) {
            for (Deal deal : property.getDeals()) {
                if (deal.getArrivalDate().getTime() <= dateOfOccupation.getTime()
                        && deal.getDepartureDate().getTime() >= dateOfOccupation.getTime()) {
                    busyEstateMap.put(property.getId(), true);
                } else {
                    busyEstateMap.put(property.getId(), false);
                }
            }
        }
        return busyEstateMap;
    }
}
