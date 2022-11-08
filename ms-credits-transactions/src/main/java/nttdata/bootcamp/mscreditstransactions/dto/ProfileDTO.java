package nttdata.bootcamp.mscreditstransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String id;
    private String profile;
    private Double maxAmount;
    private Integer maxQuantityTransactions;
    private Double commission;
}
