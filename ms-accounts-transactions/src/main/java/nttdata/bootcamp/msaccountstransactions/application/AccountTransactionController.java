package nttdata.bootcamp.msaccountstransactions.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import nttdata.bootcamp.msaccountstransactions.dto.AccountDTO;
import nttdata.bootcamp.msaccountstransactions.dto.CustomerDTO;
import nttdata.bootcamp.msaccountstransactions.dto.DebitCardDTO;
import nttdata.bootcamp.msaccountstransactions.enums.MethodTransaction;
import nttdata.bootcamp.msaccountstransactions.enums.TypeAccountTransaction;
import nttdata.bootcamp.msaccountstransactions.interfaces.IAccountService;
import nttdata.bootcamp.msaccountstransactions.interfaces.IAccountTransactionService;
import nttdata.bootcamp.msaccountstransactions.interfaces.ICustomerService;
import nttdata.bootcamp.msaccountstransactions.interfaces.IDebitCardService;
import nttdata.bootcamp.msaccountstransactions.model.AccountTransaction;

@Slf4j
@RestController
public class AccountTransactionController {
    @Autowired
    private IAccountTransactionService service;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDebitCardService debitCardService;

    @CircuitBreaker(name = "accounts-transactions", fallbackMethod = "findByNroAccountAndTypeAlt")
    @GetMapping("/{nroAccount}/{type}")
    public ResponseEntity<?> findByNroAccountAndType(@PathVariable String nroAccount, @PathVariable String type) {
        final List<AccountTransaction> response = service.findTransactionByNroAccountAndType(nroAccount, type);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> findByNroAccountAndType(@PathVariable String nroAccount, @PathVariable String type,
            Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(new ArrayList<AccountTransaction>());
    }

    @GetMapping("/{nroAccount}")
    public ResponseEntity<?> findByNroAccount(@PathVariable String nroAccount) {
        final List<AccountTransaction> response = service.findTransactionsByNroAccount(nroAccount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> createdeposit(@RequestBody AccountTransaction at) {
        try {
            String nroAccount = at.getNroAccount();

            if (at.getMethod() == null || at.getMethod().isBlank())
                at.setMethod(MethodTransaction.DIRECTO.toString());
            var validDebitCard = validateDebitCardMethod(at);
            if (validDebitCard.getStatusCodeValue() != HttpStatus.OK.value()) {
                return validDebitCard;
            }

            Optional<AccountDTO> optAccount = accountService.findByNroAccount(nroAccount);
            if (optAccount.isPresent()) {
                AccountDTO account = optAccount.get();
                Optional<CustomerDTO> optCustomer = customerService.findCustomerByNroDoc(account.getNroDoc());
                if (optCustomer.isPresent()) {
                    if (optCustomer.get().getProfileDTO() != null) {
                        List<AccountTransaction> transactions = service
                                .findTransactionsByNroAccount(account.getNroAccount());

                        if (transactions.size() > optCustomer.get().getProfileDTO().getMaxQuantityTransactions()) {
                            account.setAmount(
                                    account.getAmount() - optCustomer.get().getProfileDTO().getCommission());
                            at.setComission(optCustomer.get().getProfileDTO().getCommission());
                        }

                        account.setAmount(account.getAmount() + at.getTransactionAmount());

                        ResponseEntity<?> resp = accountService.updateAccount(account);
                        if (resp.getStatusCodeValue() == HttpStatus.OK.value()) {
                            at.setType(TypeAccountTransaction.DEPOSITO.toString());
                            at.setTransactionDate(new Date());
                            final AccountTransaction response = service.createTransaction(at);
                            return ResponseEntity.status(HttpStatus.CREATED).body(response);
                        }
                        return ResponseEntity.badRequest()
                                .body(String.format("Error al registrar el %s de la cuenta Nro: %s",
                                        TypeAccountTransaction.DEPOSITO.toString(), at.getNroAccount()));
                    }
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(String.format("No existe perfil: %s", optCustomer.get().getProfile()));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("No se encontro cliente con Nro. Documento: %s", account.getNroDoc()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No se encontró cuenta con Nro: %s", at.getNroAccount()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/retirement")
    public ResponseEntity<?> createRetirement(@RequestBody AccountTransaction at) {
        try {
            if (at.getMethod() == null || at.getMethod().isBlank())
                at.setMethod(MethodTransaction.DIRECTO.toString());
            var validDebitCard = validateDebitCardMethod(at);
            if (validDebitCard.getStatusCodeValue() != HttpStatus.OK.value()) {
                return validDebitCard;
            }

            Optional<AccountDTO> optAccount = accountService.findByNroAccount(at.getNroAccount());
            if (optAccount.isPresent()) {
                AccountDTO account = optAccount.get();
                Optional<CustomerDTO> optCustomer = customerService.findCustomerByNroDoc(account.getNroDoc());
                if (optCustomer.isPresent()) {
                    if (optCustomer.get().getProfileDTO() != null) {
                        List<AccountTransaction> transactions = service
                                .findTransactionsByNroAccount(account.getNroAccount());

                        if (transactions.size() > optCustomer.get().getProfileDTO().getMaxQuantityTransactions()) {
                            account.setAmount(
                                    account.getAmount() - optCustomer.get().getProfileDTO().getCommission());
                            at.setComission(optCustomer.get().getProfileDTO().getCommission());
                        }

                        account.setAmount(account.getAmount() - at.getTransactionAmount());

                        ResponseEntity<?> resp = accountService.updateAccount(account);
                        if (resp.getStatusCodeValue() == HttpStatus.OK.value()) {
                            at.setType(TypeAccountTransaction.RETIRO.toString());
                            at.setTransactionDate(new Date());
                            final AccountTransaction response = service.createTransaction(at);
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        }
                        return ResponseEntity.badRequest()
                                .body(String.format("Error al registrar el %s de la cuenta Nro: %s",
                                        TypeAccountTransaction.RETIRO.toString(), at.getNroAccount()));
                    }
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(String.format("No existe perfil: %s", optCustomer.get().getProfile()));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("No se encontro cliente con Nro. Documento: %s", account.getNroDoc()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No se encontró cuenta con Nro: %s", at.getNroAccount()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    private ResponseEntity<?> validateDebitCardMethod(AccountTransaction at) {
        if (at.getMethod().equals(MethodTransaction.TARJETA.toString())) {
            Optional<DebitCardDTO> optDebitCardDTO = debitCardService.findByNroCardAndNroAccount(at.getNroAccount(),
                    at.getNroCard());
            if (optDebitCardDTO.isPresent()) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ingrese una tarjeta de debito válida y vuelva a intentarlo.");
        }
        return ResponseEntity.ok().build();
    }
}
