package nttdata.bootcamp.mscredits.application;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import nttdata.bootcamp.mscredits.dto.BalanceStateDTO;
import nttdata.bootcamp.mscredits.dto.CustomerDTO;
import nttdata.bootcamp.mscredits.dto.TransactionListDTO;
import nttdata.bootcamp.mscredits.enums.CustomerTypes;
import nttdata.bootcamp.mscredits.enums.TypeTransaction;
import nttdata.bootcamp.mscredits.interfaces.ICreditCardService;
import nttdata.bootcamp.mscredits.interfaces.ICreditService;
import nttdata.bootcamp.mscredits.interfaces.ICreditTransactionService;
import nttdata.bootcamp.mscredits.interfaces.ICustomerService;
import nttdata.bootcamp.mscredits.interfaces.ITypeCreditService;
import nttdata.bootcamp.mscredits.model.Credit;
import nttdata.bootcamp.mscredits.model.CreditCard;
import nttdata.bootcamp.mscredits.model.TypeCredit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CreditController {

    @Autowired
    private ICreditService service;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITypeCreditService typeService;

    @Autowired
    private ICreditTransactionService transactionService;

    @Autowired
    private ICreditCardService cardService;

    @PostMapping
    public ResponseEntity<?> createCredit(@RequestBody Credit credit) {
        try {
            Optional<CustomerDTO> customer = customerService.findCustomerByNroDoc(credit.getNroDoc());

            if (customer.isPresent()) {
                Optional<TypeCredit> type = typeService.findByType(customer.get().getTypePerson());

                if (type.isPresent()) {
                    List<Credit> creditRecord = service.findCreditByNroDoc(credit.getNroDoc());

                    if (customer.get().getTypePerson().equals(CustomerTypes.PERSONAL.toString())
                            && creditRecord.size() == type.get().getAllowedAmount()) {
                        return ResponseEntity.badRequest().body(
                                String.format(
                                        "El cliente ya cuenta con crédito registrado y por ser de tipo PERSONAL solo puede tener un máximo de %x crédito",
                                        type.get().getAllowedAmount()));
                    }

                    Boolean hasFeeNoPayment = transactionService.validateFee(customer.get().getNroDoc());
                    if (hasFeeNoPayment) {
                        return ResponseEntity.badRequest().body(String.format(
                                "El cliente cuenta con cuotas de pago vencidas, por ello no puede adquirir un nuevo crédito."));
                    }

                    Mono<Credit> creditMono = prepareCredit(credit, customer.get());
                    final Mono<Credit> response = service.createCredit(creditMono);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                }

                List<TypeCredit> types = typeService.findAll();
                String strTypes = types.stream()
                        .map(t -> t.getType())
                        .collect(Collectors.joining(", "));
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("El tipo ingresado es invalido, los tipos validos son: [%s]",
                                strTypes));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No se encontró cliente con documento: %s", credit.getNroDoc()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message",
                                "No se encontró cliente con número de documento: " +
                                        credit.getNroDoc()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cuenta bancaria."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private Mono<Credit> prepareCredit(Credit credit, CustomerDTO customer) {
        if (credit.getDateReg() == null)
            credit.setDateReg(new Date());
        credit.setType(customer.getTypePerson());
        String uid = String.format("%040d",
                new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        if (credit.getNroCredit() == null)
            credit.setNroCredit(uid);
        if (credit.getAmountUsed() == null)
            credit.setAmountUsed(0.0);

        Mono<Credit> creditMono = Mono.just(credit);
        return creditMono;
    }

    @PutMapping
    public ResponseEntity<?> updateCredit(@RequestBody Credit credit) {
        try {
            Optional<CustomerDTO> customer = customerService.findCustomerByNroDoc(credit.getNroDoc());
            if (customer.isPresent()) {
                final Optional<Credit> resp = service.findCreditById(credit.getId());
                if (resp.isPresent()) {
                    Mono<Credit> creditMono = prepareCredit(credit, customer.get());
                    final Mono<Credit> response = service.updateCredit(creditMono.block());
                    return ResponseEntity.ok().body(response);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("No se encontró cuenta con Id: %s", credit.getId()));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No se encontró cliente con documento: %s", credit.getNroDoc()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @CircuitBreaker(name = "credits", fallbackMethod = "altFindCredits")
    @GetMapping
    public ResponseEntity<?> findCredits() {
        final Flux<Credit> response = service.findAllCredits();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> altFindCredits(Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(new ArrayList<>());
    }

    @TimeLimiter(name = "credits", fallbackMethod = "altFindById")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        final Optional<Credit> resp = service.findCreditById(id);
        if (resp.isPresent()) {
            return ResponseEntity.ok().body(resp.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> altFindById(@PathVariable String id, Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(null);
    }

    @CircuitBreaker(name = "credits", fallbackMethod = "altFindByNroCredit")
    @GetMapping("/byNroCredit/{nroCredit}")
    public ResponseEntity<Credit> findByNroCredit(@PathVariable("nroCredit") String nroCredit) {
        final Optional<Credit> resp = service.findCreditByNroCredit(nroCredit);
        if (resp.isPresent()) {
            return ResponseEntity.ok(resp.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> altFindByNroCredit(@PathVariable("nroCredit") String nroCredit, Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(null);
    }

    @CircuitBreaker(name = "credits", fallbackMethod = "getBalanceByNroCreditAlt")
    @GetMapping("/balance/{nroCredit}")
    public ResponseEntity<?> getBalanceByNroCredit(@PathVariable String nroCredit) {
        try {
            Optional<Credit> optCredit = service.findCreditByNroCredit(nroCredit);

            if (optCredit.isPresent()) {
                TransactionListDTO payments = transactionService.findTransactionByNroCreditAndType(nroCredit,
                        TypeTransaction.PAGO.toString());
                TransactionListDTO consumes = transactionService.findTransactionByNroCreditAndType(nroCredit,
                        TypeTransaction.CONSUMO.toString());
                Credit credit = optCredit.get();

                final Double totalPayments = payments.getTransactions().stream()
                        .mapToDouble(p -> p.getTransactionAmount())
                        .sum();
                final Double totalConsumes = consumes.getTransactions().stream()
                        .mapToDouble(c -> c.getTransactionAmount())
                        .sum();

                BalanceStateDTO balance = BalanceStateDTO.builder()
                        .nroDoc(credit.getNroDoc())
                        .nroCredit(credit.getNroCredit())
                        .type(credit.getType())
                        .creditLineAllowed(credit.getCreditLine())
                        .amountUsed(credit.getAmountUsed())
                        .creditLineTotal(credit.getCreditLine() + credit.getAmountUsed())
                        .payments(payments.getTransactions())
                        .consumes(consumes.getTransactions())
                        .totalPayments(totalPayments)
                        .totalConsumes(totalConsumes)
                        .build();

                return ResponseEntity.ok().body(balance);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No se encontró crédito con Nro: %s", nroCredit));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getBalanceByNroCreditAlt(@PathVariable String nroCredit, Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/nroDoc/{nroDoc}")
    public Mono<ResponseEntity<Flux<Credit>>> findByNroDoc(@PathVariable String nroDoc) {
        List<Credit> response = service.findCreditByNroDoc(nroDoc);
        List<Credit> result = response.stream().map(c -> {
            CreditCard creditCard = cardService.findCardByNroCredit(c.getNroCredit());
            c.setCreditCard(creditCard);
            return c;
        }).collect(Collectors.toList());

        Flux<Credit> respFlux = Flux.fromIterable(result);
        // respFlux.map(c -> {
        // Mono<CreditCard> creditCard =
        // cardService.findCardByNroCredit(c.getNroCredit());
        // creditCard.map(cc -> {
        // c.setCreditCard(cc);
        // return cc;
        // }).subscribe(cc -> {
        // c.setCreditCard(cc);
        // log.info("CreditCard: " + cc.toString());
        // });
        // return c;
        // }).subscribe(c -> log.info("Credit: " + c.toString()));

        return Mono.just(ResponseEntity.ok().body(respFlux));
    }
}
