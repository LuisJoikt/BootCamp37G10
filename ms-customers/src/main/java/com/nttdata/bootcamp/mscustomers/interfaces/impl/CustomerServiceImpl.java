package com.nttdata.bootcamp.mscustomers.interfaces.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccounts.model.Account;
import com.nttdata.bootcamp.mscustomers.infraestructure.ICustomerReactiveRepository;
import com.nttdata.bootcamp.mscustomers.infraestructure.ICustomerRepository;
import com.nttdata.bootcamp.mscustomers.interfaces.ICustomerService;
import com.nttdata.bootcamp.mscustomers.model.Customer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {
    
    
    @Autowired
    @Qualifier("ICustomerRepository")
    private ICustomerRepository repository;

    
    @Autowired
    @Qualifier("ICustomerReactiveRepository")
    private ICustomerReactiveRepository reactiveRepository;
    
    
    
    
    
/*
    @Override
    public Mono<Customer> createCustomer(Mono<Customer> customer) {
        return customer.flatMap(reactiveRepository::insert);
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
*/
   

    @Override
    public Mono<Customer> findCustomerById(String id) {
        return reactiveRepository.findById(id);
    }
    
    /////
    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        log.info("Create Customer",customer);
        return reactiveRepository.save(customer);
    }
    @Override
    public Flux<Customer> findAllCustomers() {
        return reactiveRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }
    @Override
    public Mono<Customer> findCustomerByNroDoc(String nroDoc) {
        return reactiveRepository.findByNroDoc(nroDoc);
    }
    @Override
    public Mono<Customer> update(Customer customer) {
    //return reactiveRepository.save(customer);
    return reactiveRepository.findById(customer.getId());
                //.map(c -> customer)
               // .flatMap(reactiveRepository::save);             
    
    }

    @Override
    public Mono<Boolean> deleteCustomer(String id) {
        return reactiveRepository.findById(id)
                .flatMap(c -> {reactiveRepository.delete(c);
                return Mono.just(true);
                
                });
    }

    

  

   


    
   
}
