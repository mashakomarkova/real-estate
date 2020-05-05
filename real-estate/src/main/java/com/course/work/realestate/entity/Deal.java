package com.course.work.realestate.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Realtor realtor;
    @ManyToOne
    private Property property;
    private Date dateOfDeal;
    private double totalPrice;
    private Date arrivalDate;
    private Date departureDate;
    private String status;

}
