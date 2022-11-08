package nttdata.bootcamp.mscredits.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscredits.model.CreditCard;
import reactor.core.publisher.Mono;

@Repository
public interface ICreditCardReactiveRepository extends ReactiveMongoRepository<CreditCard, String> {
    public Mono<CreditCard> findByNroCredit(String nroCredit);
}
