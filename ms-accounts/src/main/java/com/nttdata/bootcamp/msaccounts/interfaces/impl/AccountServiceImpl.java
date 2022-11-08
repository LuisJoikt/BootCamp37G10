package com.nttdata.bootcamp.msaccounts.interfaces.impl;

import java.text.ParseException;
import java.time.Duration;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.nttdata.bootcamp.msaccounts.config.RestConfig;
//import com.nttdata.bootcamp.msaccounts.dto.CreditDTO;
//import com.nttdata.bootcamp.msaccounts.dto.CreditListDTO;
import com.nttdata.bootcamp.msaccounts.infraestructure.IAccountReactiveRepository;
import com.nttdata.bootcamp.msaccounts.infraestructure.IAccountRepository;
import com.nttdata.bootcamp.msaccounts.interfaces.IAccountService;
import com.nttdata.bootcamp.msaccounts.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class AccountServiceImpl implements IAccountService {
    //LR
    //@Autowired
    //private RestConfig rest;
    
    @Value("${hostname}")
    private String hostname;
    //FLR
    @Autowired
    private IAccountReactiveRepository reactiveRepo;
    @Autowired
    private IAccountRepository repository;

    @Override
    public Mono<Account> createAccount(Mono<Account> account) {
        return account.flatMap(reactiveRepo::insert);
    }

    @Override
    public Flux<Account> findAllAccount() {
        return reactiveRepo.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    @Override
    public Mono<Account> updateAccount(Account account) {
        return reactiveRepo.findById(account.getId())
                .map(ba -> account)
                .flatMap(reactiveRepo::save);
    }

    @Override
    public boolean deleteAccount(String id) {
        Mono<Account> baExist = reactiveRepo.findById(id);
        if (baExist.block() != null) {
            reactiveRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Account> findAccountByNroDoc(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

    @Override
    public List<Account> findAccountByNroDocAndTypeAccount(String nroDoc, String typeAccount) {
        return repository.findByNroDocAndTypeAccount(nroDoc, typeAccount);
    }

    @Override
    public Optional<Account> findByNroAccount(String nroAccount) {
        return repository.findByNroAccount(nroAccount);
    }

    //LR
    @Override
    public Flux<Account> findAll(String nrDoc) {
        return reactiveRepo.findByNroDoc(nrDoc);
    }
    
    @Override
    public List<Account> findAccountClientProducts(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

     @Override
    public List<Account> findByNroDocAndLevel(String nroDoc, String level) throws ParseException {
        
        return repository.findByNroDocAndLevel(nroDoc,level);
    }
  
}
