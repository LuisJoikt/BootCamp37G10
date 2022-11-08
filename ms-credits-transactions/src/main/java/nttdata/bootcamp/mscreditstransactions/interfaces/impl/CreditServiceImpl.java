package nttdata.bootcamp.mscreditstransactions.interfaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import nttdata.bootcamp.mscreditstransactions.config.RestConfig;
import nttdata.bootcamp.mscreditstransactions.dto.CreditDTO;
import nttdata.bootcamp.mscreditstransactions.interfaces.ICreditService;

@Service
public class CreditServiceImpl implements ICreditService {
    @Autowired
    private RestConfig creditRest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public CreditDTO findCreditById(String id) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("id", id);
        String uri = String.format("http://%s:8090/api/credits/{id}", hostname);
        final CreditDTO dto = creditRest.getForObject(uri, CreditDTO.class, param);
        return dto;
    }

    @Override
    public ResponseEntity<?> updateCredit(CreditDTO dto) {
        HttpEntity<CreditDTO> body = new HttpEntity<CreditDTO>(dto);
        String uri = String.format("http://%s:8090/api/credits", hostname);
        ResponseEntity<CreditDTO> response = creditRest.exchange(
                uri,
                HttpMethod.PUT,
                body,
                CreditDTO.class);
        return response;
    }

    @Override
    public Optional<CreditDTO> findCreditByNroCredit(String nroCredit) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("nroCredit", nroCredit);
        String uri = String.format("http://%s:8090/api/credits/byNroCredit/{nroCredit}", hostname);
        final CreditDTO dto = creditRest.getForObject(uri, CreditDTO.class, param);
        Optional<CreditDTO> result = Optional.ofNullable(dto);
        return result;
    }

    @Override
    public List<CreditDTO> findCreditsByNroDoc(String nroDoc) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("nroDoc", nroDoc);
        String uri = String.format("http://%s:8090/api/credits/nroDoc/{nroDoc}", hostname);
        ResponseEntity<List<CreditDTO>> resp = creditRest.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CreditDTO>>() {

                }, param);
        return resp.getBody();
    }

}
