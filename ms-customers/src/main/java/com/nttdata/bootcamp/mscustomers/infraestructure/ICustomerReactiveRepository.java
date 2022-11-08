package com.nttdata.bootcamp.mscustomers.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.mscustomers.model.Customer;

@Repository("ICustomerReactiveRepository")
public interface ICustomerReactiveRepository  extends ReactiveMongoRepository<Customer,String>{
    
}
