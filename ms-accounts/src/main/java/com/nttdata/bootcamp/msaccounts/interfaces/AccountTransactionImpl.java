package com.nttdata.bootcamp.msaccounts.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.bootcamp.msaccounts.dto.AccountTransactionDTO;
import com.nttdata.bootcamp.msaccounts.dto.ListAccountTransactionDTO;


@Service
public class AccountTransactionImpl implements IAccountTransaction {
    @Autowired
    private RestTemplate rest;

    @Override
    public ListAccountTransactionDTO findTransactionByNroAccount(String nroAccount) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nroAccount", nroAccount);
        String uri = "http://localhost:8090/api/accounts_transactions/{nroAccount}";

        ResponseEntity<List<AccountTransactionDTO>> response = rest.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccountTransactionDTO>>() {
                }, params);
        ListAccountTransactionDTO dto = ListAccountTransactionDTO.builder()
                .transactions(response.getBody())
                .build();
        return dto;
    }

}
