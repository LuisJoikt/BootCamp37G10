package nttdata.bootcamp.mscreditstransactions.infraestructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscreditstransactions.model.CreditTransaction;

@Repository
public interface ICreditTransactionRepository extends MongoRepository<CreditTransaction, String> {
    public List<CreditTransaction> findByNroCreditAndType(String nroCredit, String type);

    public List<CreditTransaction> findByNroCredit(String nroCredit);

    public List<CreditTransaction> findByNroCard(String nroCard);

    public List<CreditTransaction> findByNroCardAndMethod(String nroCard, String method);
}
