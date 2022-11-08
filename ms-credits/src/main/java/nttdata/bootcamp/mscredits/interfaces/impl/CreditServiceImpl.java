package nttdata.bootcamp.mscredits.interfaces.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscredits.infraestructure.ICreditReactiveRepository;
import nttdata.bootcamp.mscredits.infraestructure.ICreditRepository;
import nttdata.bootcamp.mscredits.interfaces.ICreditService;
import nttdata.bootcamp.mscredits.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements ICreditService {

    @Autowired
    private ICreditReactiveRepository reactiveRepo;
    @Autowired
    private ICreditRepository repository;

    @Override
    public Mono<Credit> createCredit(Mono<Credit> credit) {
        return credit.flatMap(reactiveRepo::insert);
    }

    @Override
    public Flux<Credit> findAllCredits() {
        return reactiveRepo.findAll();
    }

    @Override
    public Mono<Credit> updateCredit(Credit credit) {
        return reactiveRepo.findById(credit.getId())
                .map(c -> credit)
                .flatMap(reactiveRepo::save);
    }

    @Override
    public List<Credit> findCreditByNroDoc(String nroDoc) {
        return repository.findByNroDoc(nroDoc);
    }

    @Override
    public Optional<Credit> findCreditById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Credit> findCreditByNroCredit(String nroCredit) {
        return repository.findByNroCredit(nroCredit);
    }

}
