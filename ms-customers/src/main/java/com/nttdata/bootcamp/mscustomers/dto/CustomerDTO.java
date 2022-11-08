package com.nttdata.bootcamp.mscustomers.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String typeDoc;
    private String nroDoc;
    private String phone;
    private String email;
    private String typePerson;
    private Date regDate;
    private String profile;
    private ProfileDTO profileDTO;
}
