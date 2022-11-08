package com.nttdata.bootcamp.msaccounts.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;


import com.nttdata.bootcamp.msaccounts.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
    public Mono<Account> createAccount(Mono<Account> account);

    public Flux<Account> findAllAccount();

    public Mono<Account> updateAccount(Account account);

    public boolean deleteAccount(String id);

    public List<Account> findAccountByNroDoc(String nroDoc);

    public List<Account> findAccountByNroDocAndTypeAccount(String nroDoc, String typeAccount);

    public Optional<Account> findByNroAccount(String nroAccount);

    //LR
    public Flux<Account> findAll(String nrDoc);
    public List<Account> findAccountClientProducts(String nroDoc);
    public List<Account> findByNroDocAndLevel(String nroDoc,String level) throws ParseException;

   // public CreditListDTO findClientProducts(String nrDoc);

   // public Flux<Account> findByNroDocAndfindByNroAccount(String NroDoc, String account);
    
}
