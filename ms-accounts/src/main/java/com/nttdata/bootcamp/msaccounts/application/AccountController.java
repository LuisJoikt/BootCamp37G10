package com.nttdata.bootcamp.msaccounts.application;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.nttdata.bootcamp.msaccounts.dto.AccountTransactionDTO;
import com.nttdata.bootcamp.msaccounts.dto.CreditDTO;
import com.nttdata.bootcamp.msaccounts.dto.CustomerDTO;
import com.nttdata.bootcamp.msaccounts.dto.ProductsDTO;
import com.nttdata.bootcamp.msaccounts.enums.ActionTransaction;
import com.nttdata.bootcamp.msaccounts.enums.CustomerTypes;
import com.nttdata.bootcamp.msaccounts.enums.ProfileTypes;
import com.nttdata.bootcamp.msaccounts.interfaces.IAccountService;
import com.nttdata.bootcamp.msaccounts.interfaces.IAccountTransactionService;
import com.nttdata.bootcamp.msaccounts.interfaces.ICreditService;
import com.nttdata.bootcamp.msaccounts.interfaces.ICustomerService;
import com.nttdata.bootcamp.msaccounts.model.Account;
import com.nttdata.bootcamp.msaccounts.util.ValidatorUtil;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
// LR
// @RequestMapping("/account")
// FLR
public class AccountController {
    @Autowired
    private IAccountService service;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private IAccountTransactionService transactionService;

    @Autowired
    private ICreditService creditService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Optional<CustomerDTO> existCustomer = customerService.findCustomerByNroDoc(account.getNroDoc());

            if (existCustomer.isPresent()) {

                 List<Account> existLevelMajor = service.findByNroDocAndLevel(account.getNroDoc(),"PRINCIPAL"); //LRJ
                
                 String uniqNroAccount = String.format("%040d",
                 new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
              
                CustomerDTO dto = existCustomer.get();

                account.setNroAccount(uniqNroAccount);
                UUID uid = UUID.randomUUID();
                account.setNroInterbakaryAccount(uid.toString());
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                account.setRegDate(fmt.parse(LocalDate.now().toString()));
                account.setTypeDoc(existCustomer.get().getTypeDoc());
                //LR
                if (existLevelMajor.size()>0 ) {               
                    account.setLevel("SECUNDARIA");
                    System.out.println("SI existe PRINCIPAL");
                } else {
                    account.setLevel("PRINCIPAL");
                    System.out.println("NO existe PRINCIPAL");
                }
                //FLR
                final Mono<Account> baccountMono = Mono.just(account);

                if (dto.getTypePerson().equals(CustomerTypes.PERSONAL.toString())) {
                    ResponseEntity<?> valid = validatorUtil.validatePersonalAccount(account);
                    return saveAccount(valid, baccountMono, dto);

                } else {
                    if (isNotValidPymeAccount(dto)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(String.format(
                                        "No cuenta con una tarjeta de credito asociada, para poder crear la cuenta."));
                    }
                    ResponseEntity<?> valid = validatorUtil.validateEmpresarialAccount(account);
                    return saveAccount(valid, baccountMono, dto);
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message",
                            "No se encontró cliente con número de documento: " +
                                    account.getNroDoc()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message",
                                "No se encontró cliente con número de documento: " +
                                        account.getNroDoc()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        }
    }

    private Boolean isNotValidPymeAccount(CustomerDTO customerDTO) {
        if (customerDTO.getProfile().equals(ProfileTypes.PYME.toString())) {
            List<CreditDTO> credits = creditService.findCreditsByNroDoc(customerDTO.getNroDoc());
            if (credits.size() > 0) {
                credits = credits.stream()
                        .filter(c -> c.getCreditCard() == null)
                        .collect(Collectors.toList());
                return credits.size() > 0;
            }
            return true;
        }
        return false;
    }

    private ResponseEntity<?> saveAccount(ResponseEntity<?> valid, Mono<Account> baccountMono,
            CustomerDTO customerDTO) {
        if (valid.getStatusCodeValue() == HttpStatus.OK.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(baccountMono));
        }
        return valid;
    }

    @GetMapping
    public ResponseEntity<?> findAllAccounts() {
        try {
            final Flux<Account> response = service.findAllAccount();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al obtener las cuentas bancarias."));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        try {
            if (account.getNroInterbakaryAccount() == null || account.getNroInterbakaryAccount().isEmpty()) {
                UUID uid = UUID.randomUUID();
                account.setNroInterbakaryAccount(uid.toString());
            }
            if (account.getNroAccount() == null) {
                UUID uid = UUID.randomUUID();
                account.setNroAccount(uid.toString());
            }

            if (account != null && account.getId() != null) {
                Mono<Account> response = service.updateAccount(account);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "La cuenta que intenta actualizar no existe."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al actualizar la cuenta bancaria."));
        }
    }

    @GetMapping("/byNroDoc/{nroDoc}")
    public ResponseEntity<?> findAccountByNroDoc(@PathVariable String nroDoc) {
        try {
            final List<Account> response = service.findAccountByNroDoc(nroDoc);
            Flux<Account> result = Flux.fromIterable(response);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message",
                            "Error en servidor al obetener la cuenta bancaria del cliente por número de documento."));
        }
    }

    @GetMapping("/{nroAccount}")
    public ResponseEntity<?> findByNroAccount(@PathVariable String nroAccount) {
        Optional<Account> optResp = service.findByNroAccount(nroAccount);
        if (optResp.isPresent()) {
            return ResponseEntity.ok().body(optResp.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) {
        try {
            final boolean resp = service.deleteAccount(id);
            if (resp) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "No se encontró la cuenta que intenta eliminar."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al eliminar la cuenta bancaria."));
        }
    }

    @Transactional
    @PostMapping("/pagoDeTerceros")
    public ResponseEntity<?> pagoDeTerceros(@RequestBody Account account) {
        try {
            service.updateAccount(account);
            AccountTransactionDTO dtoTransaction = AccountTransactionDTO
                    .builder()
                    .nroAccount(account.getNroAccount())
                    .detail(account.getDetailTransaction())
                    .transactionAmount(account.getAmountTransaction())
                    .build();
            ResponseEntity<AccountTransactionDTO> result = transactionService.createTransactionByAction(dtoTransaction,
                    ActionTransaction.retirement.toString());
            return result;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al actualizar la cuenta bancaria."));
        }
    }

    // LR
    @GetMapping("/ClientProducts/{nrDoc}")
    public Mono<ResponseEntity<Flux<Account>>> ClientProducts(@PathVariable String nrDoc) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(nrDoc))

        );
    }

    @GetMapping("/ClientProducts2/{nroDoc}")
    public Mono<ResponseEntity<?>> ClientProducts2(@PathVariable String nroDoc) {

        List<Account> accounts = service.findAccountClientProducts(nroDoc);

        List<CreditDTO> credits = creditService.findCreditsByNroDoc(nroDoc);

        ProductsDTO product = ProductsDTO.builder()
                .NroDoc(nroDoc)
                .accounts(accounts)
                .credits(credits)
                .build();

        return Mono.just(ResponseEntity.ok().body(Mono.just(product)));
        
    }

    // FLR

}
