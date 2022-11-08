package com.nttdata.bootcamp.msaccounts.model;

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
public class DebitCard {
    @Id
    private String nroCard;
    private String nroAccount; //eliminar
    private Date expireDate;
    private String cvc;
    private Boolean isEnabled;
}
