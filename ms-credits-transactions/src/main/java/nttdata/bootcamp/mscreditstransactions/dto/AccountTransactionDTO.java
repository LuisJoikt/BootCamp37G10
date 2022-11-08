package nttdata.bootcamp.mscreditstransactions.dto;

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
public class AccountTransactionDTO {
    private String id;
    private String nroAccount;
    private String type;
    private String detail;
    private Double transactionAmount;
    private Date transactionDate;
    private Double comission;
    private String method;
    private String nroCard;
}
