package com.nttdata.bootcamp.mscustomers.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String typeDoc;
    private String nroDoc;
    private String phone;
    private String email;
    private String typePerson;
    private Date regDate;
    private String profile;
}
