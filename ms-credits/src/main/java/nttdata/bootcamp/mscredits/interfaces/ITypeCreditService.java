package nttdata.bootcamp.mscredits.interfaces;

import java.util.List;
import java.util.Optional;

import nttdata.bootcamp.mscredits.model.TypeCredit;

public interface ITypeCreditService {
    public TypeCredit create(TypeCredit typeCredit);

    public List<TypeCredit> findAll();

    public Optional<TypeCredit> findById(String id);
    public Optional<TypeCredit> findByType(String type);

    public List<TypeCredit> createFromList(List<TypeCredit> types);

    public TypeCredit update(TypeCredit typeCredit);
}
