package com.nttdata.bootcamp.msaccounts.interfaces.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.msaccounts.infraestructure.IDevitCardRepository;
import com.nttdata.bootcamp.msaccounts.interfaces.IDebitCardService;
import com.nttdata.bootcamp.msaccounts.model.DebitCard;

@Service
public class DebitCardServiceImpl implements IDebitCardService {

    @Autowired
    private IDevitCardRepository repository;

    @Override
    public DebitCard assignCardToAccount(DebitCard debitCard) {
        return repository.insert(debitCard);
    }

    @Override
    public Optional<DebitCard> findByNroCardAndNroAccount(String nroCard, String nroAccount) {
        return repository.findByNroCardAndNroAccount(nroCard, nroAccount);
    }

}
