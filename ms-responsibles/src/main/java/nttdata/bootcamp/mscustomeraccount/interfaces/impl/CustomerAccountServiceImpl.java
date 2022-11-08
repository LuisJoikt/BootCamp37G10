package nttdata.bootcamp.mscustomeraccount.interfaces.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscustomeraccount.infraestructure.ICustomerAccountRepository;
import nttdata.bootcamp.mscustomeraccount.interfaces.ICustomerAccountService;
import nttdata.bootcamp.mscustomeraccount.model.CustomerAccount;

@Service
public class CustomerAccountServiceImpl implements ICustomerAccountService {

    @Autowired
    private ICustomerAccountRepository repository;

    @Override
    public CustomerAccount create(CustomerAccount customerAccount) {
        return repository.insert(customerAccount);
    }

    @Override
    public List<CustomerAccount> findByCustomerIdAndAccountId(String customerId, String accountId) {
        return repository.findByCustomerIdAndAccountId(customerId, accountId);
    }

}
