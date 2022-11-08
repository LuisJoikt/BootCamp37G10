package nttdata.bootcamp.mscreditstransactions.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nttdata.bootcamp.mscreditstransactions.model.CreditTransaction;
import nttdata.bootcamp.mscreditstransactions.model.PaymentSchedule;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditTransactionDTO {
    private CreditTransaction creditTransaction;
    private List<PaymentSchedule> paymentSchedules;
}
