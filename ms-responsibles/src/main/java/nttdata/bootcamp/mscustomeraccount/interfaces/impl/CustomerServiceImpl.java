package nttdata.bootcamp.mscustomeraccount.interfaces.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscustomeraccount.config.CustomerRestConfig;
import nttdata.bootcamp.mscustomeraccount.dto.CustomerDTO;
import nttdata.bootcamp.mscustomeraccount.interfaces.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRestConfig customerRest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<CustomerDTO> findCustomerById(String id) {
        Map<String, String> pathVar = new HashMap<String, String>();
        pathVar.put("id", id);
        String uri = String.format("http://%s:8090/api/customers/{id}", hostname);
        CustomerDTO dto = customerRest.getForObject(
                uri,
                CustomerDTO.class,
                pathVar);
        return Optional.ofNullable(dto);
    }

}
