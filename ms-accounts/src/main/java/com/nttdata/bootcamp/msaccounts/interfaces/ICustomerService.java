package com.nttdata.bootcamp.msaccounts.interfaces;

import java.text.ParseException;
import java.util.Optional;

import com.nttdata.bootcamp.msaccounts.dto.CustomerDTO;

public interface ICustomerService {
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc) throws ParseException;
    
}
