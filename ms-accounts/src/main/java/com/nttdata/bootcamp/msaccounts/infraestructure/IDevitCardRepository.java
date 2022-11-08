package com.nttdata.bootcamp.msaccounts.infraestructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msaccounts.model.DebitCard;

@Repository
public interface IDevitCardRepository extends MongoRepository<DebitCard, String> {
    public Optional<DebitCard> findByNroCardAndNroAccount(String nroCard, String nroAccount);
}
