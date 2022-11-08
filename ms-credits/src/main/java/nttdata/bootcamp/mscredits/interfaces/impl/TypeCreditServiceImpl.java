package nttdata.bootcamp.mscredits.interfaces.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscredits.infraestructure.ITypeCreditRepository;
import nttdata.bootcamp.mscredits.interfaces.ITypeCreditService;
import nttdata.bootcamp.mscredits.model.TypeCredit;

@Service
public class TypeCreditServiceImpl implements ITypeCreditService {

    @Autowired
    private ITypeCreditRepository repository;

    @Override
    public List<TypeCredit> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TypeCredit> findByType(String type) {
        return repository.findByType(type);
    }

    @Override
    public TypeCredit create(TypeCredit typeCredit) {
        return repository.insert(typeCredit);
    }

    @Override
    public List<TypeCredit> createFromList(List<TypeCredit> types) {
        return repository.insert(types);
    }

    @Override
    public TypeCredit update(TypeCredit typeCredit) {
        return repository.save(typeCredit);
    }

    @Override
    public Optional<TypeCredit> findById(String id) {
        return repository.findById(id);
    }
}
