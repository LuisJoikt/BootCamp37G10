package nttdata.bootcamp.msaccountstransactions.interfaces;

import java.util.List;

import nttdata.bootcamp.msaccountstransactions.model.AccountTransaction;

public interface IAccountTransactionService {
    public AccountTransaction createTransaction(AccountTransaction at);

    public AccountTransaction updateTransaction(AccountTransaction at);

    public List<AccountTransaction> findTransactionByNroAccountAndType(String nroAccount, String type);

    public List<AccountTransaction> findTransactionsByNroAccount(String nroAccount);
}
