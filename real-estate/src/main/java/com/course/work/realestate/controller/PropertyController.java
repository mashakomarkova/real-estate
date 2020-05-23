package com.course.work.realestate.controller;

import com.course.work.realestate.constant.Constant;
import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;
import com.course.work.realestate.entity.User;
import com.course.work.realestate.service.DealService;
import com.course.work.realestate.service.DistrictService;
import com.course.work.realestate.service.PropertyService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class PropertyController {

    private PropertyService propertyService;
    private DistrictService districtService;
    private DealService dealService;

    public PropertyController(PropertyService propertyService, DistrictService districtService,
                              DealService dealService) {
        this.propertyService = propertyService;
        this.districtService = districtService;
        this.dealService = dealService;
    }

    @GetMapping("/filterProperty")
    public ModelAndView propertySearcherPage(HttpServletRequest request) {
        System.out.println("User " + request.getSession().getAttribute("user"));
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        return modelAndView;
    }

    @PostMapping("/searchProperty")
    public ModelAndView searchPropertyByDate(@RequestParam String arrivalDate, @RequestParam String departureDate,
                                             @RequestParam String district, @RequestParam String numberOfRooms,
                                             @RequestParam String priceFrom, @RequestParam String priceTo) {
        Date arrivalDateOfOccupation = Date.valueOf(arrivalDate);
        Date departureDateOfOccupation = Date.valueOf(departureDate);
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        if (!areDatesValid(arrivalDateOfOccupation, departureDateOfOccupation)) {
            modelAndView.addObject("error", "Дата прибытия не может быть позже даты высоления");
            return modelAndView;
        } else {
            List<Property> properties = propertyService.findPropertiesByFilters(
                    !numberOfRooms.isEmpty() ? Integer.parseInt(numberOfRooms) : null,
                    !district.isEmpty() ? new Gson().fromJson(district, District.class) : null,
                    !priceFrom.isEmpty() ? Double.parseDouble(priceFrom) : null,
                    !priceTo.isEmpty() ? Double.parseDouble(priceTo) : null
            );
            Map<Long, Boolean> busyEstateMap = new HashMap<>();
            for (Iterator<Property> it = properties.iterator(); it.hasNext(); ) {
                Property property = it.next();
                for (Deal deal : property.getDeals()) {
                    if (!(arrivalDateOfOccupation.getTime() <= deal.getArrivalDate().getTime()
                            && departureDateOfOccupation.getTime() <= deal.getArrivalDate().getTime()
                            || arrivalDateOfOccupation.getTime() >= deal.getDepartureDate().getTime()
                            && departureDateOfOccupation.getTime() > arrivalDateOfOccupation.getTime())) {
                        if (deal.getStatus().equals(Constant.STATUS_CANCELED)) {
                            busyEstateMap.put(property.getId(), false);
                        } else {
                            busyEstateMap.put(property.getId(), true);
                        }
                    } else {
                        busyEstateMap.put(property.getId(), false);
                    }
                }
            }
            modelAndView.addObject("properties", properties);
            modelAndView.addObject("arrivalDate", arrivalDate);
            modelAndView.addObject("departureDate", departureDate);
            modelAndView.addObject("busyEstateMap", busyEstateMap);
            return modelAndView;
        }
    }

    private boolean areDatesValid(Date arrivalDate, Date departureDate) {
        return arrivalDate.before(departureDate);
    }

    @PostMapping("/bookProperty")
    public ModelAndView bookProperty(@RequestParam String arrivalDate, @RequestParam String departureDate,
                                     @RequestParam String propertyId, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("propertySearcher");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        Deal deal = getDeal(arrivalDate, departureDate, propertyId, request);
        dealService.saveDeal(deal);
        return modelAndView;
    }

    @GetMapping("/bookings")
    public ModelAndView bookings(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("bookings");
        modelAndView.addObject("deals", dealService.findDealsByClient(
                (User) request.getSession().getAttribute("user")));
        return modelAndView;
    }

    @GetMapping("/cancelBooking/{id}")
    public ModelAndView cancelBooking(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/bookings");
        Deal deal = dealService.findDealById(Long.parseLong(id));
        Date today = new Date(System.currentTimeMillis());
        if (deal.getArrivalDate().before(today) && deal.getDepartureDate().before(today)
                || deal.getStatus().equals(Constant.STATUS_CANCELED)
                || deal.getStatus().equals(Constant.STATUS_COMPLETED)) {
            modelAndView.addObject("incorrect", "Вы не можете отменить эту сделку");
        } else {
            deal.setStatus(Constant.STATUS_CANCELED);
            dealService.saveDeal(deal);
        }
        return modelAndView;
    }

    private Deal getDeal(String arrivalDate, String departureDate, String propertyId, HttpServletRequest request) {
        Deal deal = new Deal();
        Date arrivalDateSql = Date.valueOf(arrivalDate);
        deal.setArrivalDate(arrivalDateSql);
        Date departureDateSql = Date.valueOf(departureDate);
        deal.setDepartureDate(departureDateSql);
        deal.setDateOfDeal(Date.valueOf(LocalDate.now()));
        User client = (User) request.getSession().getAttribute("user");
        deal.setClient(client);
        deal.setStatus(Constant.STATUS_ACCEPTED);
        Property property = propertyService.findPropertyById(Long.parseLong(propertyId));
        property.getDeals().add(deal);
        deal.setProperty(property);
        double totalPrice = property.getPrice() * ((departureDateSql.getTime() - arrivalDateSql.getTime()) / (3600000 * 24));
        deal.setTotalPrice(totalPrice);
        return deal;
    }

    @GetMapping("/propertyById/{id}")
    public ModelAndView findPropertyById(@PathVariable Long id, @RequestParam String arrivalDate,
                                         @RequestParam String departureDate) {
        ModelAndView modelAndView = new ModelAndView("property");
        modelAndView.addObject("property", propertyService.findPropertyById(id));
        modelAndView.addObject("property", propertyService.findPropertyById(id));
        modelAndView.addObject("arrivalDate", arrivalDate);
        modelAndView.addObject("departureDate", departureDate);
        return modelAndView;
    }

    @GetMapping("/propertyManagement")
    public ModelAndView propertyManagement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("propertyManagement");
        modelAndView.addObject("properties",
                propertyService.findPropertiesByRealtor((User) request.getSession().getAttribute("user")));
        modelAndView.addObject("districts", districtService.findAllDistricts());
        return modelAndView;
    }

    @GetMapping("/deleteProperty/{id}")
    public ModelAndView deleteProperty(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/propertyManagement");
        propertyService.deleteProperty(id);
        return modelAndView;
    }

    @GetMapping("/detailsProperty/{id}")
    public ModelAndView detailsProperty(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("propertyDetails");
        modelAndView.addObject("districts", districtService.findAllDistricts());
        Property property = propertyService.findPropertyById(id);
        modelAndView.addObject("property", property);
        modelAndView.addObject("totalCompletedDeals", (int) property.getDeals()
                .stream().filter(deal -> deal.getStatus().equals(Constant.STATUS_COMPLETED)).count());
        modelAndView.addObject("totalCanceledDeals", (int) property.getDeals()
                .stream().filter(deal -> deal.getStatus().equals(Constant.STATUS_CANCELED)).count());
        modelAndView.addObject("totalActiveDeals", (int) property.getDeals()
                .stream().filter(deal -> deal.getStatus().equals(Constant.STATUS_ACCEPTED)).count());
        modelAndView.addObject("totalProfit", property.getDeals().stream()
                .filter(deal -> deal.getStatus().equals(Constant.STATUS_COMPLETED))
                .mapToDouble(Deal::getTotalPrice)
                .sum()
        );
        return modelAndView;
    }

    @PostMapping("/addProperty")
    public ModelAndView addProperty(@RequestParam String district, @RequestParam String numberOfRooms,
                                    @RequestParam String price, @RequestParam String type,
                                    @RequestParam String area, @RequestParam String description,
                                    HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:/propertyManagement");
        Property property = new Property();
        property.setRealtor((User) request.getSession().getAttribute("user"));
        property.setDistrict(new Gson().fromJson(district, District.class));
        property.setNumberOfRooms(Integer.parseInt(numberOfRooms));
        property.setPrice(Double.parseDouble(price));
        property.setType(type);
        property.setArea(Double.parseDouble(area));
        property.setDescription(description);
        propertyService.saveProperty(property);
        return modelAndView;
    }

    @PostMapping("/updateProperty")
    public ModelAndView updateProperty(@RequestParam String id, @RequestParam String district,
                                       @RequestParam String numberOfRooms,
                                       @RequestParam String price, @RequestParam String type,
                                       @RequestParam String area, @RequestParam String description,
                                       HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:/detailsProperty/" + id);
        Property property = propertyService.findPropertyById(Long.parseLong(id));
        property.setId(Long.parseLong(id));
        property.setDistrict(new Gson().fromJson(district, District.class));
        property.setNumberOfRooms(Integer.parseInt(numberOfRooms));
        property.setPrice(Double.parseDouble(price));
        property.setType(type);
        property.setArea(Double.parseDouble(area));
        property.setDescription(description);
        propertyService.saveProperty(property);
        return modelAndView;
    }

    @PostMapping("/saveReport")
    public ModelAndView saveReport(@RequestParam String id, @RequestParam String totalCompletedDeals,
                                   @RequestParam String totalCanceledDeals, @RequestParam String totalActiveDeals,
                                   @RequestParam String totalProfit) {
        ModelAndView modelAndView = new ModelAndView("redirect:/detailsProperty/" + id);
        Property propertyJson = propertyService.findPropertyById(Long.parseLong(id));
        int totalCompletedDealsJson = Integer.parseInt(totalCompletedDeals);
        int totalCanceledDealsJson = Integer.parseInt(totalCanceledDeals);
        int totalActiveDealsJson = Integer.parseInt(totalActiveDeals);
        double totalProfitJson = Double.parseDouble(totalProfit);
        propertyService.saveReport(propertyJson, totalCompletedDealsJson, totalCanceledDealsJson,
                totalActiveDealsJson, totalProfitJson);
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
