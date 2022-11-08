package nttdata.bootcamp.mscreditstransactions.interfaces.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscreditstransactions.infraestructure.ICreditTransactionRepository;
import nttdata.bootcamp.mscreditstransactions.interfaces.ICreditTransactionService;
import nttdata.bootcamp.mscreditstransactions.model.CreditTransaction;

@Service
public class CreditTransactionServiceImpl implements ICreditTransactionService {

    @Autowired
    private ICreditTransactionRepository repository;

    @Override
    public List<CreditTransaction> findTransactionByNroCreditAndType(String nroCredit, String type) {
        return repository.findByNroCreditAndType(nroCredit, type);
    }

    @Override
    public CreditTransaction createTransaction(CreditTransaction ct) {
        return repository.insert(ct);
    }

    @Override
    public CreditTransaction updateTransaction(CreditTransaction ct) {
        return repository.save(ct);
    }

    @Override
    public List<CreditTransaction> findTransactionsByNroCredit(String nroCredit) {
        return repository.findByNroCredit(nroCredit);
    }

    @Override
    public List<CreditTransaction> getLastTransactionByMethodAndNroCard(String method, String nroCard) {
        return repository.findByNroCardAndMethod(nroCard, method);
    }

}
