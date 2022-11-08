package com.nttdata.bootcamp.mscustomers.model;

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
@Document("profiles")
public class Profile {
    @Id
    private String id;
    private String profile;
    private Double maxAmount;
    private Integer maxQuantityTransactions;
    private Double commission;
}
