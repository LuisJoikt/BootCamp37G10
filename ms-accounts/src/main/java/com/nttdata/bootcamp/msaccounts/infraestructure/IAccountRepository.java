package com.nttdata.bootcamp.msaccounts.infraestructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msaccounts.model.Account;

@Repository("IAccountRepository")
public interface IAccountRepository extends MongoRepository<Account, String> {
    public List<Account> findByNroDoc(String nroDoc);

    public List<Account> findByNroDocAndTypeAccount(String nroDoc, String typeAccount);

    public Optional<Account> findByNroAccount(String nroAccount);

    //LR
    public List<Account> findByNroDocAndLevel(String nroDoc,String level);
    //FLR


    
}
