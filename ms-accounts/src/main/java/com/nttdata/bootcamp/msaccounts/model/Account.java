package com.nttdata.bootcamp.msaccounts.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
////@data = revisar
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("accounts")
public class Account {
    @Id
    private String id;
    private String nroAccount;
    private String typeAccount;
    private Double amount;
    private String typeDoc;
    private String nroDoc;
    private Date regDate;
    private String nroInterbakaryAccount;
    @Transient
    private String detailTransaction;
    @Transient
    private Double amountTransaction;
    private String nroCard;
    private String level;
}
