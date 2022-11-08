package nttdata.bootcamp.mscreditstransactions.interfaces.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.mscreditstransactions.infraestructure.IPaymentScheduleRepository;
import nttdata.bootcamp.mscreditstransactions.interfaces.IPaymentScheduleService;
import nttdata.bootcamp.mscreditstransactions.model.PaymentSchedule;

@Service
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {
    @Autowired
    private IPaymentScheduleRepository repository;

    @Override
    public PaymentSchedule create(PaymentSchedule paymentSchedule) {
        return repository.insert(paymentSchedule);
    }

    @Override
    public List<PaymentSchedule> findByNroCredit(String nroCredit) {
        return repository.findByNroCredit(nroCredit);
    }

    @Override
    public List<PaymentSchedule> createFromList(List<PaymentSchedule> paymentSchedules) {
        return repository.insert(paymentSchedules);
    }

    @Override
    public PaymentSchedule update(PaymentSchedule paymentSchedule) {
        return repository.save(paymentSchedule);
    }

    @Override
    public List<PaymentSchedule> findByNroCreditAndState(String nroCredit, String statePayFee) {
        return repository.findByNroCreditAndStatePayFee(nroCredit, statePayFee);
    }

}
