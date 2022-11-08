package nttdata.bootcamp.mscustomeraccount.application;

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

import nttdata.bootcamp.mscustomeraccount.dto.CustomerDTO;
import nttdata.bootcamp.mscustomeraccount.enums.CustomerAccountType;
import nttdata.bootcamp.mscustomeraccount.enums.CustomerTypes;
import nttdata.bootcamp.mscustomeraccount.interfaces.ICustomerAccountService;
import nttdata.bootcamp.mscustomeraccount.interfaces.ICustomerService;
import nttdata.bootcamp.mscustomeraccount.model.CustomerAccount;
import reactor.core.publisher.Flux;

@RestController
public class CustomerAccountController {

    @Autowired
    private ICustomerAccountService service;

    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerAccount cAccount) {
        if (cAccount.getAccountId().isBlank() || cAccount.getCustomerId().isBlank() || cAccount.getType().isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Campos incompletos, verifique los campos: CustomerId, AccountId y Type.");
        }

        Optional<CustomerDTO> validCustomer = customerService.findCustomerById(cAccount.getCustomerId());
        if (validCustomer.isPresent()) {
            if (validCustomer.get().getTypePerson().equals(CustomerTypes.EMPRESARIAL.toString())) {
                cAccount.setRegDate(new Date());
                if (cAccount.getType().equals(CustomerAccountType.TITULAR.toString())
                        || cAccount.getType().equals(CustomerAccountType.FIRMANTE_AUTORIZADO.toString())) {
                    final CustomerAccount response = service.create(cAccount);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                }
                return ResponseEntity.badRequest()
                        .body(String.format("Tipo de asignación inválida, solo se admite los tipos: %s y %s",
                                CustomerAccountType.TITULAR, CustomerAccountType.FIRMANTE_AUTORIZADO));
            }
            return ResponseEntity.badRequest()
                    .body("El responsable que intenta asignar no es de tipo: " + CustomerTypes.EMPRESARIAL.toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("No se encontró cliente con Id: %", cAccount.getId()));
    }

    @GetMapping("/{custId}/{accountId}")
    public ResponseEntity<?> findByCustomerIdAndAccountId(@PathVariable("custId") String custId,
            @PathVariable("accountId") String accountId) {
        if (custId.isBlank() || accountId.isBlank()) {
            return ResponseEntity.badRequest().body("¡Parámetros incompletos!");
        }

        List<CustomerAccount> response = service.findByCustomerIdAndAccountId(custId, accountId);
        Flux<CustomerAccount> result = Flux.fromIterable(response);
        return ResponseEntity.ok(result);
    }
}
