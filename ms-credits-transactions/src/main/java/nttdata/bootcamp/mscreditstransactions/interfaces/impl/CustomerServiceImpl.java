package nttdata.bootcamp.mscreditstransactions.interfaces.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import nttdata.bootcamp.mscreditstransactions.config.RestConfig;
import nttdata.bootcamp.mscreditstransactions.dto.CustomerDTO;
import nttdata.bootcamp.mscreditstransactions.dto.ProfileDTO;
import nttdata.bootcamp.mscreditstransactions.interfaces.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<ProfileDTO> findProfileByProfile(String profile) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("profile", profile);
        String uri = String.format("http://%s:8090/api/customers/profiles/{profile}", hostname);
        final ProfileDTO dto = rest.getForObject(uri, ProfileDTO.class, param);
        return Optional.ofNullable(dto);
    }

    @Override
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("nroDoc", nroDoc);
        String uri = String.format("http://%s:8090/api/customers/byNroDoc/{nroDoc}", hostname);
        CustomerDTO dto = rest.getForObject(
                uri,
                CustomerDTO.class,
                param);
        return Optional.ofNullable(dto);
    }

}
