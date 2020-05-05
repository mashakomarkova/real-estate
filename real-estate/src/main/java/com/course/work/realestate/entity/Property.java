package com.course.work.realestate.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private District district;
    private int numberOfRooms;
    private double price;
    private String type;
    private double area;
    private String description;
}
