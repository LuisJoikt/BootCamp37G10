package com.nttdata.bootcamp.mscustomers.interfaces.impl;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.mscustomers.infraestructure.ICustomerReactiveRepository;
import com.nttdata.bootcamp.mscustomers.infraestructure.ICustomerRepository;
import com.nttdata.bootcamp.mscustomers.interfaces.ICustomerService;
import com.nttdata.bootcamp.mscustomers.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    @Qualifier("ICustomerRepository")
    private ICustomerRepository repository;

    @Autowired
    @Qualifier("ICustomerReactiveRepository")
    private ICustomerReactiveRepository reactiveRepository;

    @Override
    public Mono<Customer> createCustomer(Mono<Customer> customer) {
        return customer.flatMap(reactiveRepository::insert);
    }

    @Override
    public Flux<Customer> findAllCustomers() {
        return reactiveRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer) {
        return reactiveRepository.findById(customer.getId())
                .map(c -> customer)
                .flatMap(reactiveRepository::save);
    }

    @Override
    public boolean deleteCustomer(String id) {
        Mono<Customer> cusFinded = reactiveRepository.findById(id);
        if (cusFinded.block() != null) {
            reactiveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Customer> findCustomerByNroDoc(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

    @Override
    public Mono<Customer> findCustomerById(String id) {
        return reactiveRepository.findById(id);
    }

}
