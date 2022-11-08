package nttdata.bootcamp.mscredits.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("credits")
public class Credit {
    @Id
    private String id;
    private String nroDoc;
    private String nroCredit;
    private Double creditLine;
    private Double amountUsed;
    private String type;
    private Date dateReg;
    @Transient
    private CreditCard creditCard;
}
