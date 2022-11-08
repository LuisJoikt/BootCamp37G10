package nttdata.bootcamp.mscredits.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
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
@Document("creditCard")
public class CreditCard {
    @Id
    private String nroCard;
    private String nroCredit;
    private Date expireDate;
    private String cvc;
    private Boolean isEnabled;
}
