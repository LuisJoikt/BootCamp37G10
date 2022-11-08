package nttdata.bootcamp.mscreditstransactions.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscreditstransactions.model.CreditTransaction;
import reactor.core.publisher.Flux;

@Repository("ICreditTransactionReactiveRepository")
public interface ICreditTransactionReactiveRepository extends ReactiveMongoRepository<CreditTransaction, String> {
    public Flux<CreditTransaction> findByNroCardAndMethod(String nroCard, String method);
}
