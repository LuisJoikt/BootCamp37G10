package nttdata.bootcamp.mscredits.interfaces.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import nttdata.bootcamp.mscredits.config.RestConfig;
import nttdata.bootcamp.mscredits.dto.CustomerDTO;
import nttdata.bootcamp.mscredits.interfaces.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private RestConfig rest;

    @Value("${hostname}")
    private String hostname;

    @Override
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc) throws ParseException {
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
