package nttdata.bootcamp.mscreditstransactions.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {
    private String id;
    private String nroDoc;
    private String nroCredit;
    private Double creditLine;
    private Double amountUsed;
    private String type;
    private Date dateReg;
}
