package nttdata.bootcamp.mscredits.interfaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import nttdata.bootcamp.mscredits.config.RestConfig;
import nttdata.bootcamp.mscredits.dto.CreditTransactionDTO;
import nttdata.bootcamp.mscredits.dto.TransactionListDTO;
import nttdata.bootcamp.mscredits.interfaces.ICreditTransactionService;

@Service
public class CreditTransactionServiceImpl implements ICreditTransactionService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public TransactionListDTO findTransactionByNroCreditAndType(String nroCredit, String type) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nroCredit", nroCredit);
        params.put("type", type);
        String uri = String.format("http://%s:8090/api/credits_transactions/{nroCredit}/{type}", hostname);

        ResponseEntity<List<CreditTransactionDTO>> response = rest.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CreditTransactionDTO>>() {
                }, params);
        TransactionListDTO dto = TransactionListDTO.builder()
                .transactions(response.getBody())
                .build();
        return dto;
    }

    @Override
    public Boolean validateFee(String nroDoc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nroDoc", nroDoc);
        String uri = String.format("http://%s:8090/api/credits_transactions/validateFee/{nroDoc}", hostname);
        Boolean resp = rest.getForObject(uri, Boolean.class, params);
        return resp;
    }

}
