package nttdata.bootcamp.mscredits.interfaces;

import nttdata.bootcamp.mscredits.dto.TransactionListDTO;

public interface ICreditTransactionService {
    public TransactionListDTO findTransactionByNroCreditAndType(String nroCredit, String type);

    public Boolean validateFee(String nroDoc);
}
