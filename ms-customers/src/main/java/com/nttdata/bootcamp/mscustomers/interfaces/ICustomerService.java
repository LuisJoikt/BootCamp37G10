package com.nttdata.bootcamp.mscustomers.interfaces;

import com.nttdata.bootcamp.mscustomers.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    

   // public Optional<Customer> findCustomerByNroDoc(String nroDoc);

  


    public Mono<Customer> findCustomerById(String id);

    ////
    public Flux<Customer> findAllCustomers();
    public Mono<Customer> findCustomerByNroDoc(String nroDoc);
    public Mono<Customer> createCustomer(Customer customer); 
    public Mono<Customer> update(Customer customer);
    public Mono<Boolean> deleteCustomer (String id);
}
