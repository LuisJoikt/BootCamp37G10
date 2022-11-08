package com.nttdata.bootcamp.msaccounts.interfaces;

import java.util.List;

import com.nttdata.bootcamp.msaccounts.dto.CreditDTO;

public interface ICreditService {
    public List<CreditDTO> findCreditsByNroDoc(String nroDoc);
}
