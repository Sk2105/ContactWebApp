package com.sk.contactapi.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Contact {


    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String id;

    private String name;

    private String email;

    private String phone;

    private int age;

    private String address;

    private boolean active;

    private String imageUrl;
}
