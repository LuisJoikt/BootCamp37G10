package nttdata.bootcamp.msaccountstransactions.dto;

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
public class DebitCardDTO {
    private String nroCard;
    private String nroAccount;
    private Date expireDate;
    private String cvc;
    private Boolean isEnabled;
}
