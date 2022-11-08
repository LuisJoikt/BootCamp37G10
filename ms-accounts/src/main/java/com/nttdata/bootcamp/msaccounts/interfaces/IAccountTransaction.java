package com.nttdata.bootcamp.msaccounts.interfaces;

import com.nttdata.bootcamp.msaccounts.dto.ListAccountTransactionDTO;

public interface IAccountTransaction {
    public ListAccountTransactionDTO findTransactionByNroAccount (String nroAccount);
}
