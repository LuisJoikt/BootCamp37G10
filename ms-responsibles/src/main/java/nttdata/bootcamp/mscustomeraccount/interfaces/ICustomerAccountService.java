package nttdata.bootcamp.mscustomeraccount.interfaces;

import java.util.List;

import nttdata.bootcamp.mscustomeraccount.model.CustomerAccount;

public interface ICustomerAccountService {
    public CustomerAccount create(CustomerAccount customerAccount);

    public List<CustomerAccount> findByCustomerIdAndAccountId(String customerId, String accountId);
}
