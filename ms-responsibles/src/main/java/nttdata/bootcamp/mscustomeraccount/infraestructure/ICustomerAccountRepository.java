package nttdata.bootcamp.mscustomeraccount.infraestructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscustomeraccount.model.CustomerAccount;

@Repository
public interface ICustomerAccountRepository extends MongoRepository<CustomerAccount, String> {
    public List<CustomerAccount> findByCustomerIdAndAccountId(String customerId, String accountId);
}
