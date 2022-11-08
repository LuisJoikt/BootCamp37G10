package nttdata.bootcamp.msaccountstransactions.interfaces.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.msaccountstransactions.config.RestConfig;
import nttdata.bootcamp.msaccountstransactions.dto.DebitCardDTO;
import nttdata.bootcamp.msaccountstransactions.interfaces.IDebitCardService;

@Service
public class DebitCardServiceImpl implements IDebitCardService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<DebitCardDTO> findByNroCardAndNroAccount(String nroAccount, String nroCard) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("nroAccount", nroAccount);
        params.put("nroCard", nroCard);
        String uri = String.format("http://%s:8090/api/accounts/debitCard/{nroCard}/{nroAccount}", hostname);
        DebitCardDTO resp = rest.getForObject(uri, DebitCardDTO.class, params);
        return Optional.ofNullable(resp);
    }

}
