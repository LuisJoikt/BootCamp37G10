package nttdata.bootcamp.mscreditstransactions.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CreditTransaction {
    @Id
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
