package nttdata.bootcamp.mscredits.interfaces;

import java.text.ParseException;
import java.util.Optional;

import nttdata.bootcamp.mscredits.dto.CustomerDTO;

public interface ICustomerService {
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc) throws ParseException;
}
