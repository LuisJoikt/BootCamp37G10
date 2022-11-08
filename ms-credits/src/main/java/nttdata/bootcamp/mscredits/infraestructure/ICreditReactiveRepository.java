package nttdata.bootcamp.mscredits.infraestructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscredits.model.Credit;
import reactor.core.publisher.Flux;

@Repository
public interface ICreditReactiveRepository extends ReactiveMongoRepository<Credit, String> {
    public Flux<Credit> findByNroDoc(String nroDoc);
}
