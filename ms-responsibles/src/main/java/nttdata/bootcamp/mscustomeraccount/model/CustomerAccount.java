package nttdata.bootcamp.mscustomeraccount.model;

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
public class CustomerAccount {
    @Id
    private String id;
    private String customerId;
    private String accountId;
    private String type;
    private Date regDate;
}
