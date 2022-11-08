package nttdata.bootcamp.mscreditstransactions.interfaces;

import java.util.List;

import nttdata.bootcamp.mscreditstransactions.model.PaymentSchedule;

public interface IPaymentScheduleService {
    public PaymentSchedule create(PaymentSchedule paymentSchedule);

    public List<PaymentSchedule> findByNroCredit(String nroCredit);

    public List<PaymentSchedule> createFromList(List<PaymentSchedule> paymentSchedules);

    public PaymentSchedule update(PaymentSchedule paymentSchedule);

    public List<PaymentSchedule> findByNroCreditAndState(String nroCredit, String statePayFee);
}
