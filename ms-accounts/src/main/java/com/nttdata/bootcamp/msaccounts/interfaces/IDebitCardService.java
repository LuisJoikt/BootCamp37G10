package com.nttdata.bootcamp.msaccounts.interfaces;

import java.util.Optional;

import com.nttdata.bootcamp.msaccounts.model.DebitCard;

public interface IDebitCardService {
    public DebitCard assignCardToAccount(DebitCard debitCard);

    public Optional<DebitCard> findByNroCardAndNroAccount(String nroCard, String nroAccount);
}
