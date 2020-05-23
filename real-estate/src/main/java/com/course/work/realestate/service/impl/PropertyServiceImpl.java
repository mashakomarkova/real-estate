package com.course.work.realestate.service.impl;

import com.course.work.realestate.entity.Deal;
import com.course.work.realestate.entity.District;
import com.course.work.realestate.entity.Property;
import com.course.work.realestate.entity.User;
import com.course.work.realestate.repository.PropertyRepository;
import com.course.work.realestate.service.PropertyService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
                if (priceTo != null && priceFrom == null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("price"), priceTo));
                }
                if (priceTo == null && priceFrom != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("price"), priceFrom));
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return propertyRepository.findAll(specification);
    }

    @Override
    public List<Property> findAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public List<Property> findPropertiesByRealtor(User user) {
        return propertyRepository.findAllByRealtor(user);
    }

    @Override
    public Property findPropertyById(Long id) {
        return propertyRepository.findById(id).get();
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }

    @Override
    public void saveReport(Property property, int totalCompletedDealsJson, int totalCanceledDealsJson, int totalActiveDeals,
                           double totalProfit) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Report");
        int rownum = 0;
        Cell cell;
        Row row;
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("client username");
        // EmpName
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("client first name");
        // Salary
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("client last name");
        // Grade
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("client phone number");
        // Bonus
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("dateOfDeal");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("totalPrice");

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("arrivalDate");

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("departureDate");

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("status");

        for (Deal deal : property.getDeals()) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(deal.getClient().getUsername());

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(deal.getClient().getFirstName());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(deal.getClient().getLastName());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(deal.getClient().getPhoneNumber());

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(deal.getDateOfDeal().toString());

            cell = row.createCell(5, CellType.NUMERIC);
            cell.setCellValue(deal.getTotalPrice());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(deal.getArrivalDate().toString());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(deal.getDepartureDate().toString());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(deal.getStatus());
        }
        rownum++;

        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("total completed deals");

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("total cancelled deals");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("total active deals");

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("profit");

        rownum++;

        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(totalCompletedDealsJson);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(totalCanceledDealsJson);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(totalActiveDeals);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue(totalProfit);

        File file = new File("deals.xls");

        try {
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
