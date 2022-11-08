package com.nttdata.bootcamp.msaccounts.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msaccounts.model.Account;

import reactor.core.publisher.Flux;

@Repository
public interface IAccountReactiveRepository extends ReactiveMongoRepository<Account, String> {
public Flux<Account> findByNroDoc(String nroDoc);

//public Flux<Account> findByNroDocAndfindByNroAccount(String nroDoc,String nroAccount);  
      // Flux<Account> listar();

   

   // public Flux<Account> FindBy
}
