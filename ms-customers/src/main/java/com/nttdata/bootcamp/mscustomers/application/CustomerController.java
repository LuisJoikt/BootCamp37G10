package com.nttdata.bootcamp.mscustomers.application;

import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//import com.nttdata.bootcamp.mscustomers.enums.CustomerTypes;
//import com.nttdata.bootcamp.mscustomers.enums.ProfileTypes;
import com.nttdata.bootcamp.mscustomers.interfaces.ICustomerService;
//import com.nttdata.bootcamp.mscustomers.interfaces.IProfileService;
import com.nttdata.bootcamp.mscustomers.model.Customer;
//import com.nttdata.bootcamp.mscustomers.model.Profile;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RefreshScope
@RestController
public class CustomerController {
    @Autowired
    private ICustomerService service;

    @Value("message.demo")
    private String demoString;
//////////////////////////////////////////////////
@PostMapping("/createCustomer")
public Mono<Customer> createCustomer(@RequestBody Customer request){
        return service.createCustomer(request);
}

@GetMapping("/findAll")
public Flux<Customer> findAll(){
return service.findAllCustomers();
}

@GetMapping("/findBynroDoc/{nroDoc}")
public Mono<Customer>finByDni(@PathVariable String nroDoc){
return service.findCustomerByNroDoc(nroDoc);
}

@PutMapping("/upDateBynroDoc")
    public Mono<ResponseEntity<Customer>> upDateBynroDoc(@RequestBody Customer customer) {
        return service.update(customer)
            .flatMap(c -> Mono.just(ResponseEntity.ok().body(c)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
}

@DeleteMapping("/deleteCustomer/{id}")
public Mono<ResponseEntity<Boolean>>deleteCustomer(@PathVariable String id){
    return service.deleteCustomer(id)
            .flatMap(c -> Mono.just(ResponseEntity.ok().body(c)))
            .defaultIfEmpty(ResponseEntity.notFound().build());
            
    
} 

//////////////////////////////////////////////////////
 /*   @PostMapping
    public ResponseEntity<?> createCustomer1(@RequestBody Customer customer) {
        try {
            if (customer != null && customer.getTypePerson() != null
                    && (customer.getTypePerson().equals(CustomerTypes.PERSONAL.toString())
                            || customer.getTypePerson().equals(CustomerTypes.EMPRESARIAL.toString()))) {

                ResponseEntity<?> respProfile = buildProfile(customer);
                if (respProfile.getStatusCodeValue() != HttpStatus.OK.value()) {
                    return respProfile;
                }

                customer.setRegDate(new Date());
                final Mono<Customer> customerMono = Mono.just(customer);
                final Mono<Customer> response = service.createCustomer(customerMono);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(
                            "message",
                            "El cliente solo puede ser de tipo " +
                                    CustomerTypes.PERSONAL.toString() +
                                    " o de tipo " +
                                    CustomerTypes.EMPRESARIAL.toString() + "."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error al crear cliente."));
        }
    }

    private ResponseEntity<?> buildProfile(Customer customer) {
        if (customer.getProfile() == null || customer.getProfile().isBlank()
                || customer.getProfile().equals("general".toUpperCase())) {
            customer.setProfile("GENERAL");
        } else {
            Optional<Profile> optProfile = serviceProfile.findByProfile(customer.getProfile());

            if (!optProfile.isPresent()) {
                List<Profile> profiles = serviceProfile.findAll();
                String strProfiles = profiles.stream()
                        .map(p -> p.getProfile())
                        .collect(Collectors.joining(", "));
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("Perfil invalido, los perfiles válidos son: [%s]", strProfiles));
            }

            customer.setProfile(
                    customer.getTypePerson().equals(CustomerTypes.PERSONAL.toString())
                            ? ProfileTypes.VIP.toString()
                            : ProfileTypes.PYME.toString());
        }

        return ResponseEntity.ok(customer);
    }
*/ 
    @CircuitBreaker(name = "customers")
    @TimeLimiter(name = "customers", fallbackMethod = "alternativeFindAllCustomers")
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public CompletableFuture<ResponseEntity<?>> findAllCustomers() {
        try {
            log.info(demoString);
            final Flux<Customer> response = service.findAllCustomers();
            return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(response));
        } catch (Exception e) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al obtener los datos de clientes.")));
        }
    }

    public CompletableFuture<ResponseEntity<?>> alternativeFindAllCustomers(Exception ex) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }
/* 
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try {
            if (customer != null && (customer.getTypePerson().equals(CustomerTypes.PERSONAL.toString())
                    || customer.getTypePerson().equals(CustomerTypes.EMPRESARIAL.toString()))) {
                customer.setRegDate(new Date());

                ResponseEntity<?> respProfile = buildProfile(customer);
                if (respProfile.getStatusCodeValue() != HttpStatus.OK.value()) {
                    return respProfile;
                }

                if (customer.getRegDate() == null) {
                    customer.setRegDate(new Date());
                }
                Mono<Customer> response = service.updateCustomer(customer);
                if (response != null) {
                    return ResponseEntity.ok(response);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(
                                "message",
                                "El cliente que intenta actualizar no existe."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(
                            "message",
                            "El cliente solo puede ser de tipo " +
                                    CustomerTypes.PERSONAL.toString() +
                                    " o de tipo " +
                                    CustomerTypes.EMPRESARIAL.toString() + "."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error en servidor al actualizar cliente."));
        }
    }
    
 
    @GetMapping("/byNroDoc/{nroDoc}")
    public ResponseEntity<?> findCustomerByNroDoc(@PathVariable String nroDoc) {
        try {
            final Optional<Customer> response = service.findCustomerByNroDoc(nroDoc);
            if (response.isPresent()) {
                Customer c = response.get();
                Optional<Profile> optProfile = serviceProfile.findByProfile(c.getProfile());
                ProfileDTO dtoProfile = optProfile.isPresent() ? ProfileDTO.builder()
                        .id(optProfile.get().getId())
                        .profile(optProfile.get().getProfile())
                        .maxAmount(optProfile.get().getMaxAmount())
                        .maxQuantityTransactions(optProfile.get().getMaxQuantityTransactions())
                        .commission(optProfile.get().getCommission())
                        .build() : null;

                CustomerDTO dto = CustomerDTO.builder()
                        .id(c.getId())
                        .firstName(c.getFirstName())
                        .lastName(c.getLastName())
                        .typeDoc(c.getTypeDoc())
                        .nroDoc(c.getNroDoc())
                        .phone(c.getPhone())
                        .email(c.getEmail())
                        .typePerson(c.getTypePerson())
                        .regDate(c.getRegDate())
                        .profile(c.getProfile())
                        .profileDTO(dtoProfile)
                        .build();
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable String id) {
        try {
            final Mono<Customer> response = service.findCustomerById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
