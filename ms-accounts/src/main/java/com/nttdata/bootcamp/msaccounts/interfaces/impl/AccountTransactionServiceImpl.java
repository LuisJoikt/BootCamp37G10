package com.nttdata.bootcamp.msaccounts.interfaces.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccounts.config.RestConfig;
import com.nttdata.bootcamp.msaccounts.dto.AccountTransactionDTO;
import com.nttdata.bootcamp.msaccounts.interfaces.IAccountTransactionService;

@Service
public class AccountTransactionServiceImpl implements IAccountTransactionService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public ResponseEntity<AccountTransactionDTO> createTransactionByAction(AccountTransactionDTO dto, String action) {
        HttpEntity<AccountTransactionDTO> body = new HttpEntity<AccountTransactionDTO>(dto);
        String uri = String.format("http://%s:8090/api/accounts_transactions/%s", hostname, action);
        ResponseEntity<AccountTransactionDTO> resp = rest.exchange(
                uri,
                HttpMethod.POST,
                body,
                AccountTransactionDTO.class);
        return resp;
    }

}
