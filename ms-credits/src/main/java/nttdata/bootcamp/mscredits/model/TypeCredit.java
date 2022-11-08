package nttdata.bootcamp.mscredits.model;

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
@Document("types_credits")
public class TypeCredit {
    @Id
    private String id;
    private String type;
    private Integer allowedAmount;
}
