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
public class CreditCardDTO {
    private String nroCard;
    private String nroCredit;
    private Date expireDate;
    private String cvc;
    private Boolean isEnabled;
}
