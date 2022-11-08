package nttdata.bootcamp.mscredits.interfaces;

import java.util.Optional;

import nttdata.bootcamp.mscredits.model.CreditCard;
import reactor.core.publisher.Mono;

public interface ICreditCardService {
    public Mono<CreditCard> associateCardToCredit(CreditCard creditCard);

    public Mono<CreditCard> cancelCard(CreditCard creditCard);

    public CreditCard findCardByNroCredit(String nroCredit);

    public Optional<CreditCard> findByNroCardAndNroCredit(String nroCard, String nroCredit);
}
