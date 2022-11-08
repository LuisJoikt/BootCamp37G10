package nttdata.bootcamp.mscredits.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TransactionListDTO {
    private List<CreditTransactionDTO> transactions;

    public TransactionListDTO() {
        transactions = new ArrayList<CreditTransactionDTO>();
    }
}
