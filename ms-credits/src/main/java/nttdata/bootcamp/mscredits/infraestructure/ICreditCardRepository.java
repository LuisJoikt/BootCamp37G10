package nttdata.bootcamp.mscredits.infraestructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscredits.model.CreditCard;

@Repository
public interface ICreditCardRepository extends MongoRepository<CreditCard, String> {
    public CreditCard findByNroCredit(String nroCredit);

    public Optional<CreditCard> findByNroCardAndNroCredit(String nroCard, String nroCredit);
}
