package nttdata.bootcamp.mscredits.infraestructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscredits.model.Credit;

@Repository
public interface ICreditRepository extends MongoRepository<Credit, String> {
    public List<Credit> findByNroDoc(String nroDoc);

    public Optional<Credit> findByNroCredit(String nroCredit);
}
