package com.nttdata.bootcamp.msaccounts.interfaces;

import org.springframework.http.ResponseEntity;

import com.nttdata.bootcamp.msaccounts.dto.AccountTransactionDTO;

public interface IAccountTransactionService {
    public ResponseEntity<AccountTransactionDTO> createTransactionByAction(AccountTransactionDTO dto, String action);
}
