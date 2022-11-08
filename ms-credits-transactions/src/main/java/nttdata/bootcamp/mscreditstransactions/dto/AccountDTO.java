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
public class AccountDTO {
    private String id;
    private String nroAccount;
    private String typeAccount;
    private Double contableBalance;
    private Double amount;
    private String typeDoc;
    private String nroDoc;
    private Date regDate;
    private String nroInterbakaryAccount;
    private String detailTransaction;
    private Double amountTransaction;
}
