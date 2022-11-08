package nttdata.bootcamp.mscredits.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceStateDTO {
    private String nroDoc;
    private String nroCredit;
    private String type;
    private Double creditLineAllowed;
    private Double amountUsed;
    private Double creditLineTotal;
    private List<CreditTransactionDTO> payments;
    private List<CreditTransactionDTO> consumes;
    private Double totalPayments;
    private Double totalConsumes;
}
