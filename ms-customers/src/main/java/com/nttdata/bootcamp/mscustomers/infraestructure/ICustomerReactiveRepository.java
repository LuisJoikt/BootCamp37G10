package com.nttdata.bootcamp.mscustomers.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.mscustomers.model.Customer;

import reactor.core.publisher.Mono;

@Repository("ICustomerReactiveRepository")
public interface ICustomerReactiveRepository  extends ReactiveMongoRepository<Customer,String>{
    public Mono<Customer> findByNroDoc(String nroDoc);
    public Mono<Customer> findById(String id);
    
    //public Mono<Customer>  upDateBynroDoc(String nroDoc);
}
