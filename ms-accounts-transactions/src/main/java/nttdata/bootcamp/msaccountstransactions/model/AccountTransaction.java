package nttdata.bootcamp.msaccountstransactions.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {
    @Id
    private String id;
    private String nroAccount;
    private String type;
    private String detail;
    private Double transactionAmount;
    private Date transactionDate;
    private Double comission;
    private String method;
    private String nroCard;
    private Double before;
    private Double transactionLater;
}
