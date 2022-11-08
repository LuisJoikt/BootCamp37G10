package nttdata.bootcamp.mscredits.infraestructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscredits.model.TypeCredit;

@Repository
public interface ITypeCreditRepository extends MongoRepository<TypeCredit, String> {
    public Optional<TypeCredit> findByType(String type);
}
