package nttdata.bootcamp.mscredits.interfaces.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscredits.infraestructure.ICreditCardReactiveRepository;
import nttdata.bootcamp.mscredits.infraestructure.ICreditCardRepository;
import nttdata.bootcamp.mscredits.interfaces.ICreditCardService;
import nttdata.bootcamp.mscredits.model.CreditCard;
import reactor.core.publisher.Mono;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

    @Autowired
    private ICreditCardReactiveRepository reactiveRepository;
    @Autowired
    private ICreditCardRepository repository;

    @Override
    public Mono<CreditCard> associateCardToCredit(CreditCard creditCard) {
        return reactiveRepository.insert(creditCard);
    }

    @Override
    public Mono<CreditCard> cancelCard(CreditCard creditCard) {
        return reactiveRepository.findById(creditCard.getNroCard())
                .map(c -> creditCard)
                .flatMap(reactiveRepository::save);
    }

    @Override
    public CreditCard findCardByNroCredit(String nroCredit) {
        return repository.findByNroCredit(nroCredit);
    }

    @Override
    public Optional<CreditCard> findByNroCardAndNroCredit(String nroCard, String nroCredit) {
        return repository.findByNroCardAndNroCredit(nroCard, nroCredit);
    }

}
