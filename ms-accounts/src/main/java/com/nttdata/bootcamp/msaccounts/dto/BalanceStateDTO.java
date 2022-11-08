package com.nttdata.bootcamp.msaccounts.dto;

import java.util.List;

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
public class BalanceStateDTO {
    
    private String nroDoc;
    private String nroAccount;
    private String type;
    private Double transactionAccount;
    private Double transactionLater;
    private Double before;

    private List<AccountTransactionDTO> ahorroRes;

   // private Double totalPayments;
    //private Double totalConsumes;
}
