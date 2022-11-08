package nttdata.bootcamp.mscreditstransactions.interfaces;

import java.util.Optional;

import nttdata.bootcamp.mscreditstransactions.dto.CreditCardDTO;

public interface ICreditCardService {
    public Optional<CreditCardDTO> findByNroCardAndNroCredit(String nroCard, String nroCredit);
}
