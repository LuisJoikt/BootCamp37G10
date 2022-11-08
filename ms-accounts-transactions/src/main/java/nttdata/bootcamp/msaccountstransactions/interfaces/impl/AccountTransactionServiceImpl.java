package nttdata.bootcamp.msaccountstransactions.interfaces.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.msaccountstransactions.infraestructure.IAccountTransactionRepository;
import nttdata.bootcamp.msaccountstransactions.interfaces.IAccountTransactionService;
import nttdata.bootcamp.msaccountstransactions.model.AccountTransaction;

@Service
public class AccountTransactionServiceImpl implements IAccountTransactionService {

    @Autowired
    private IAccountTransactionRepository repository;

    @Override
    public AccountTransaction createTransaction(AccountTransaction at) {
        return repository.insert(at);
    }

    @Override
    public AccountTransaction updateTransaction(AccountTransaction at) {
        return repository.save(at);
    }

    @Override
    public List<AccountTransaction> findTransactionByNroAccountAndType(String nroAccount, String type) {
        return repository.findByNroAccountAndType(nroAccount, type);
    }

    @Override
    public List<AccountTransaction> findTransactionsByNroAccount(String nroAccount) {
        return repository.findByNroAccount(nroAccount);
    }

}
