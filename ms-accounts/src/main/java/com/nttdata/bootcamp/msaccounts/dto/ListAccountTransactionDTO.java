package com.nttdata.bootcamp.msaccounts.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor


public class ListAccountTransactionDTO {
    private List<AccountTransactionDTO> transactions;

    public ListAccountTransactionDTO(){
        transactions = new ArrayList<AccountTransactionDTO>();
    }
}
