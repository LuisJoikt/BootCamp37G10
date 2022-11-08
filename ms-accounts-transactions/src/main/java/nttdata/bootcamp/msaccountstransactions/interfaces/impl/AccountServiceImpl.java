package nttdata.bootcamp.msaccountstransactions.interfaces.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.msaccountstransactions.config.RestConfig;
import nttdata.bootcamp.msaccountstransactions.dto.AccountDTO;
import nttdata.bootcamp.msaccountstransactions.interfaces.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<AccountDTO> findByNroAccount(String nroAccount) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("nroAccount", nroAccount);
        String uri = String.format("http://%s:8090/api/accounts/{nroAccount}", hostname);
        AccountDTO dto = rest.getForObject(uri, AccountDTO.class, param);
        return Optional.ofNullable(dto);
    }

    @Override
    public ResponseEntity<?> updateAccount(AccountDTO dto) {
        HttpEntity<AccountDTO> body = new HttpEntity<AccountDTO>(dto);
        String uri = String.format("http://%s:8090/api/accounts", hostname);
        ResponseEntity<AccountDTO> response = rest.exchange(
                uri,
                HttpMethod.PUT,
                body,
                AccountDTO.class);
        return response;
    }

}
