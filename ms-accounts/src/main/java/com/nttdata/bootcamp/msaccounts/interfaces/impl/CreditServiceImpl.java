package com.nttdata.bootcamp.msaccounts.interfaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccounts.config.RestConfig;
import com.nttdata.bootcamp.msaccounts.dto.CreditDTO;
import com.nttdata.bootcamp.msaccounts.interfaces.ICreditService;

@Service
public class CreditServiceImpl implements ICreditService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public List<CreditDTO> findCreditsByNroDoc(String nroDoc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nroDoc", nroDoc);
        String uri = String.format("http://%s:8090/api/credits/nroDoc/{nroDoc}", hostname);
        ResponseEntity<List<CreditDTO>> response = rest.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CreditDTO>>() {
                }, params);
        List<CreditDTO> resp = response.getBody();

        return resp;
    }

}
