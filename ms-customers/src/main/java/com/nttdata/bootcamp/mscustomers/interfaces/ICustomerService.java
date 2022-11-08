package com.nttdata.bootcamp.mscustomers.interfaces;

import java.util.Optional;

import com.nttdata.bootcamp.mscustomers.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    public Mono<Customer> createCustomer(Mono<Customer> customer);

    public Flux<Customer> findAllCustomers();

    public Optional<Customer> findCustomerByNroDoc(String nroDoc);

    public Mono<Customer> updateCustomer(Customer customer);

    public boolean deleteCustomer(String id);

    public Mono<Customer> findCustomerById(String id);
}
