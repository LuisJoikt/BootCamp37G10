package nttdata.bootcamp.mscreditstransactions.interfaces;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import nttdata.bootcamp.mscreditstransactions.dto.AccountDTO;
import nttdata.bootcamp.mscreditstransactions.dto.AccountTransactionDTO;

public interface IAccountService {
    public Optional<AccountDTO> findAccountByNro(String nroAccount);

    public ResponseEntity<AccountDTO> updateAccount(AccountDTO accountDTO);

    public ResponseEntity<AccountTransactionDTO> pagoDeTerceros(AccountDTO accountDTO);
}
