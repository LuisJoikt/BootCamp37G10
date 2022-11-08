package nttdata.bootcamp.mscredits.interfaces;

import java.util.List;
import java.util.Optional;

import nttdata.bootcamp.mscredits.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {
    public Mono<Credit> createCredit(Mono<Credit> credit);

    public Flux<Credit> findAllCredits();

    public Mono<Credit> updateCredit(Credit credit);

    public List<Credit> findCreditByNroDoc(String nroDoc);

    public Optional<Credit> findCreditById(String id);

    public Optional<Credit> findCreditByNroCredit(String nroCredit);
}
