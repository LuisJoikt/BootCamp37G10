package nttdata.bootcamp.mscredits.dto;

import java.util.Date;

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
public class CreditTransactionDTO {
    private String id;
    private String nroCredit;
    private String type;
    private String detail;
    private Double transactionAmount;
    private Date transactionDate;
    // CANTIDAD DE CUOTAS MENSUALES
    private Double feeMonths;
    private String originAccount;
    private String method;
    private String nroCard;
}
