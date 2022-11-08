package nttdata.bootcamp.mscustomeraccount.interfaces;

import java.util.Optional;

import nttdata.bootcamp.mscustomeraccount.dto.CustomerDTO;

public interface ICustomerService {
    public Optional<CustomerDTO> findCustomerById(String id);
}
