package com.nttdata.bootcamp.msaccounts.dto;

import java.util.List;

import com.nttdata.bootcamp.msaccounts.model.Account;

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
public class ProductsDTO {

    private String NroDoc;
    private List<Account> accounts;
    private List<CreditDTO> credits;

}
