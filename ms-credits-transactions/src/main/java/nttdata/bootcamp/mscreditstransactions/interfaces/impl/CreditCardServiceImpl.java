package nttdata.bootcamp.mscreditstransactions.interfaces.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscreditstransactions.config.RestConfig;
import nttdata.bootcamp.mscreditstransactions.dto.CreditCardDTO;
import nttdata.bootcamp.mscreditstransactions.interfaces.ICreditCardService;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<CreditCardDTO> findByNroCardAndNroCredit(String nroCard, String nroCredit) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nroCard", nroCard);
        params.put("nroCredit", nroCredit);
        String uri = String.format("http://%s:8090/api/credits/creditCard/{nroCard}/{nroCredit}", hostname);
        CreditCardDTO resp = rest.getForObject(uri, CreditCardDTO.class, params);
        return Optional.ofNullable(resp);
    }

}
