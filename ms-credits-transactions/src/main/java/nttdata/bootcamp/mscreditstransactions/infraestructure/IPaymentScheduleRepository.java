package nttdata.bootcamp.mscreditstransactions.infraestructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.mscreditstransactions.model.PaymentSchedule;

@Repository
public interface IPaymentScheduleRepository extends MongoRepository<PaymentSchedule, String> {
    public List<PaymentSchedule> findByNroCredit(String nroCredit);

    public List<PaymentSchedule> findByNroCreditAndStatePayFee(String nroCredit, String statePayFee);
}
