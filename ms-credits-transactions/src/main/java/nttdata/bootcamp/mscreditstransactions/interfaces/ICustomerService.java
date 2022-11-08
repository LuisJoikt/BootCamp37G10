package nttdata.bootcamp.mscreditstransactions.interfaces;

import java.util.Optional;

import nttdata.bootcamp.mscreditstransactions.dto.CustomerDTO;
import nttdata.bootcamp.mscreditstransactions.dto.ProfileDTO;

public interface ICustomerService {
    public Optional<CustomerDTO> findCustomerByNroDoc(String nroDoc);

    public Optional<ProfileDTO> findProfileByProfile(String profile);
}
