package com.nttdata.bootcamp.mscustomers.dto;

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
public class ProfileDTO {
    private String id;
    private String profile;
    private Double maxAmount;
    private Integer maxQuantityTransactions;
    private Double commission;
}
