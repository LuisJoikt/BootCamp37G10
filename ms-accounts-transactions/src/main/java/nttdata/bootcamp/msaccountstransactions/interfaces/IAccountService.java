package nttdata.bootcamp.msaccountstransactions.interfaces;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import nttdata.bootcamp.msaccountstransactions.dto.AccountDTO;

public interface IAccountService {
    public Optional<AccountDTO> findByNroAccount(String nroAccount);

    public ResponseEntity<?> updateAccount(AccountDTO dto);
}
