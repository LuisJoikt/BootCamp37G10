package nttdata.bootcamp.msaccountstransactions.interfaces;

import java.util.Optional;

import nttdata.bootcamp.msaccountstransactions.dto.CustomerDTO;
import nttdata.bootcamp.msaccountstransactions.dto.ProfileDTO;


public interface ICustomerService {
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc);

    public Optional<ProfileDTO> findProfileByProfile(String profile);
}
