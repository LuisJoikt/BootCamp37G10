package com.nttdata.bootcamp.msaccounts.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {
    private String id;
    private String nroDoc;
    private String nroCredit;
    private Double creditLine;
    private Double amountUsed;
    private String type;
    private Date dateReg;
    private CreditCardDTO creditCard;
}
