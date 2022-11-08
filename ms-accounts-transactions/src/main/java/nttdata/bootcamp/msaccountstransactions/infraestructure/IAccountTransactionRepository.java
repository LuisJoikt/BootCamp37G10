package nttdata.bootcamp.msaccountstransactions.infraestructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.msaccountstransactions.model.AccountTransaction;

@Repository
public interface IAccountTransactionRepository extends MongoRepository<AccountTransaction, String> {
    public List<AccountTransaction> findByNroAccountAndType(String nroAccount, String type);

    public List<AccountTransaction> findByNroAccount(String nroAccount);
}
