package nttdata.bootcamp.msaccountstransactions.interfaces;

import java.util.Optional;

import nttdata.bootcamp.msaccountstransactions.dto.DebitCardDTO;

public interface IDebitCardService {
    public Optional<DebitCardDTO> findByNroCardAndNroAccount(String nroCredit, String nroAccount);
}
